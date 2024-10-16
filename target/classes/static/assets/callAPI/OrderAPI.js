function sendOrderData(check) {
  const order = check === "insert" ? getPlaceOrderData() : getOrderData();
  const method = check === "insert" ? "POST" : "PUT";
  const endpoint = check === "insert" ? "placeOrder" : "update";
  const url = check === "insert" ? "/" : "/admin/Order";

  const paymentMethod = order.paymentMethod;
  const pricetoal = order.finalTotal.replace(/[^0-9]/g, ""); // Lấy phần chữ số
  if (paymentMethod === "Transfer") {
    $.ajax({
      url: "/api/payments/createcheckout",
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify({
        finalTotal: pricetoal,
        orderId: order.orderID,
        returnUrl: "http://localhost:8080/yourOrder",
        cancelUrl: "http://localhost:8080/checkout",
      }),
      success: function (response) {
        console.log("Gọi API thành công:", response);
        window.location.href = response;
      },
      error: function (xhr, status, error) {
        console.error("Có lỗi xảy ra:", error);
      },
    });
  } else {
    sendRequest(method, "order", endpoint, order, url);
  }
}

function updateOrderData() {
  const order = getOrderData();
  const method = "PUT";
  const endpoint = "update";
  const url = "/yourOrder";
  sendRequest(method, "order", endpoint, order, url);
}
function deleteOrder(id) {
  sendRequest("DELETE", "order", `delete/${id}`, id, "delete");
}
