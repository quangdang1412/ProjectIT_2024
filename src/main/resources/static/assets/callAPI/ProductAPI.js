function addToCart(productId) {
  let quantityElement = document.getElementById("quantityInput");
  let quantity = quantityElement ? quantityElement.value : 1;
  console.log(quantity);
  const token = localStorage.getItem("token");
  if (token === null) window.location.href = "/login";
  else {
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
          const Toast = Swal.mixin({
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            didOpen: (toast) => {
              toast.onmouseenter = Swal.stopTimer;
              toast.onmouseleave = Swal.resumeTimer;
            },
          });
          Toast.fire({
            icon: "success",
            title: response,
          });
          // Cập nhật số lượng sản phẩm trong giỏ hàng trên giao diện
          let countElement = document.getElementById("countProductInCart");
          let currentCount = parseInt(countElement.textContent) || 0;
          countElement.textContent = currentCount + parseInt(quantity); // Cộng thêm số sản phẩm đã thêm
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
        const Toast = Swal.mixin({
          toast: true,
          position: "top-end",
          showConfirmButton: false,
          timer: 3000,
          timerProgressBar: true,
          didOpen: (toast) => {
            toast.onmouseenter = Swal.stopTimer;
            toast.onmouseleave = Swal.resumeTimer;
          },
        });
        Toast.fire({
          icon: "success",
          title: response,
        });
        $(`tr:has(a[onclick*='${productId}'])`).remove();
        // let countElement = document.getElementById("countProductInCart");
        // let currentCount = parseInt(countElement.textContent) || 0;
        // countElement.textContent = Math.max(currentCount - 1, 0); // Trừ đi 1 sản phẩm hoặc giữ giá trị tối thiểu là 0
        calculateTotal();
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
  const htmlContent = tinymce.get("productDescription").getContent();
  document.getElementById("descriptionInput").value = htmlContent;
  var form = document.getElementById("formID");
  var data = new FormData(form);
  const method = check === "insert" ? "POST" : "PUT";
  const endpoint2 = check === "insert" ? "add" : "update";
  sendRequest(method, "product", endpoint2, data, "/admin/Product");
}
function deleteProduct(id) {
  const $link = $(`a[onclick*='${id}']`);
  const currentStatus = $link.text().trim();
  let str;
  if (currentStatus === "Active") str = "kích hoạt";
  else str = "vô hiệu hóa";
  const swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: "btn btn-success",
      cancelButton: "btn btn-danger",
    },
  });
  swalWithBootstrapButtons
    .fire({
      title: `Bạn có muốn ${str} sản phẩm không?`,
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Yes",
      cancelButtonText: "No",
      reverseButtons: true,
    })
    .then((result) => {
      if (result.isConfirmed) {
        sendRequest("DELETE", "product", `delete/${id}`, id, "delete");
      }
    });
}
function formatCurrency(amount) {
  return amount.toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
function calculateTotal() {
  let table = document.getElementById("myTable");
  let totalCells = table.querySelectorAll("tbody .total p");
  let total = 0;

  totalCells.forEach((cell) => {
    total += parseFloat(cell.innerText.replace(/[^0-9.-]+/g, ""));
  });
  document.getElementById("totalAmount").textContent =
    formatCurrency(total) + " VND";

  document.getElementById("quantityCart").textContent = totalCells.length;
  document.getElementById("countProductInCart").textContent = totalCells.length;

  console.log(totalCells.length);
}
