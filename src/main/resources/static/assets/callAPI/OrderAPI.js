function sendOrderData(check) {
    const order = check === "insert" ? getPlaceOrderData() : getOrderData();
    const method = check === "insert" ? "POST" : "PUT";
    const endpoint = check === "insert" ? "placeOrder" : "update";
    const url = check === "insert" ? "/" : "/admin/Order";

    const paymentMethod = order.paymentMethod;
    const finalTotal =
        check === "insert"
            ? order.finalTotal.replace(/[^0-9]/g, "")
            : order.finalTotal;
    if (paymentMethod === "Transfer") {
        localStorage.setItem("orderId", order.orderID);
        $.ajax({
            url: "/api/order/placeOrder",
            type: "POST",
            contentType: "application/json", // Đảm bảo định dạng là JSON
            headers: {
                Authorization: "Bearer " + localStorage.getItem("token"),
            },//Lê Thanh Phong
            data: JSON.stringify(order), // Truyền dữ liệu order
            success: function (response) {
                if (response.status == 201) {
                    $.ajax({
                        url: "/api/payments/createcheckout",
                        type: "POST",
                        contentType: "application/json",
                        data: JSON.stringify({
                            finalTotal: finalTotal,
                            orderId: order.orderID,
                            returnUrl: "http://localhost:8080/checkout",
                            cancelUrl: "http://localhost:8080/checkout",
                        }),
                        success: function (response_payment) {
                            console.log("Gọi API thành công:", response_payment);
                            window.location.href = response_payment;
                        },
                        error: function (xhr, status, error) {
                            console.error("Có lỗi xảy ra trong payment API:", error);
                        },
                    });
                } else {
                    Swal.fire({
                        icon: "error",
                        title: "Thông báo.",
                        text: response.message || "Có lỗi xảy ra.",
                    });
                }
            },
            error: function (xhr, status, error) {
                let response = JSON.parse(xhr.responseText);
                Swal.fire({
                    icon: "error",
                    title: "Thông báo.",
                    text: response.message,
                });
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

function updateYourOrderData() {
    const order = getOrderData();
    const finalTotal = order.totalPrice.replace(/[^0-9]/g, "");
    $.ajax({
        url: "/api/payments/createcheckout",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            finalTotal: finalTotal,
            orderId: order.orderID,
            returnUrl: window.location.href,
            cancelUrl: window.location.href,
        }),
        success: function (response) {
            console.log("Gọi API thành công:", response);
            window.location.href = response;
        },
        error: function (xhr, status, error) {
            console.error("Có lỗi xảy ra:", error);
        },
    });
}

function deleteOrder(id) {
    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: "btn btn-success",
            cancelButton: "btn btn-danger",
        },
    });
    swalWithBootstrapButtons
        .fire({
            title: "Bạn có muốn xóa đơn hàng không?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonText: "Yes, delete it!",
            cancelButtonText: "No, cancel!",
            reverseButtons: true,
        })
        .then((result) => {
            if (result.isConfirmed) {
                sendRequest("DELETE", "order", `delete/${id}`, id, "delete");
            }
        });
}
