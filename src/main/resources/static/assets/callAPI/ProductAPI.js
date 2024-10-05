function addToCart(productId) {
  let quantityElement = document.getElementById("quantityInput");
  let quantity = quantityElement ? quantityElement.value : 1;
  console.log(quantity);
  const token = localStorage.getItem("token")
  if(token === null)
    window.location.href = "/login";
  else{
    $.ajax({
      url: "/api/addtocart/" + productId + "/" + quantity,
      type: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token"), // Include JWT token if necessary
      },
      success: function (response) {
        console.log("Server response:", response); // Debugging log
        if (response.includes("Đã cập nhật sản phẩm vào giỏ hàng")) {
          Swal.fire({
            icon: "success",
            title: "Thông báo.",
            text: response,
          });
        } else {
          Swal.fire({
            icon: "error",
            title: "Thông báo.",
            text: response,
          });
        }
      },
    });
  }
}
function deleteProinCart(productId) {
  $.ajax({
    url: "/api/deleteproduct/" + productId,
    type: "DELETE",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + localStorage.getItem("token"), // Include JWT token if necessary
    },
    success: function (response) {
      console.log("Server response:", response); // Debugging log
      // Assume the response is a string that indicates success or error
      if (response.includes("Đã xóa sản phẩm khỏi giỏ hàng")) {
        Swal.fire({
          icon: "success",
          title: "Thông báo.",
          text: response,
        });
        // Remove the table row
        $(`tr:has(a[onclick*='${productId}'])`).remove();
      } else {
        Swal.fire({
          icon: "error",
          title: "Thông báo.",
          text: response,
        });
      }
    },
  });
}

function sendProductData(check) {
  var form = document.getElementById("formID");
  var data = new FormData(form);
  const method = check === "insert" ? "POST" : "PUT";
  const endpoint2 = check === "insert" ? "add" : "update";
  sendRequest(method, "product", endpoint2, data, "/admin/Product");
}
function deleteProduct(id) {
  sendRequest("DELETE", "product", `delete/${id}`, id, "delete");
}
