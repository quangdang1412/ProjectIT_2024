function getUserData() {
  return {
    userID: getValue("UserID"),
    userName: getValue("UserName"),
    password: getValue("Password"),
    email: getValue("Email"),
    phone: getValue("Phone"),
    gender: getValue("Gender", "select"),
    address: getValue("Address"),
    type: getValue("Type", "select"),
  };
}
function getProductData() {
  return {
    productID: getValue("ProductID"),
    productName: getValue("ProductName"),
    brand: getValue("BrandID", "select"),
    category: getValue("CategoryID", "select"),
    discount: getValue("DiscountID", "select"),
    supplierID: getValue("SupplierID", "select"),
    unitCost: getValue("UnitCost"),
    unitPrice: getValue("UnitPrice"),
    quantity: getValue("Quantity"),
    fileImage: getFile(),
    description: getValue("Description", "textarea"),
  };
}
function getFile() {
  const fileInput = document.querySelector('input[name="ImageCode"]');
  if (fileInput.files.length > 0) {
    return fileInput.files[0];
  }
  return null;
}
function getOrderData() {
  return {
    orderID: getValue("OrderID"),
    sellerID: getValue("SellerID", "select"),
    shipperID: getValue("ShipperID", "select"),
    orderDate: getValue("OrderDate"),
    deliveryTime: getValue("DeliveryTime"),
    name: getValue("Name"),
    phone: getValue("Phone"),
    address: getValue("Address"),
    totalPrice: getValue("TotalPrice"),
    transportFee: getValue("TransportFee"),
    paymentMethod: getValue("paymentMethod"),
    paymentStatus: getValue("paymentStatus", "select"),
    status: getValue("Status", "select"),
  };
}
function getPlaceOrderData() {
  return {
    orderID: getValue("OrderID"),
    name: getValue("Name"),
    phone: getValue("Phone"),
    address: `${getValue("Address")}, ${getDropdownValue(
      "communeDropdown"
    )}, ${getDropdownValue("districtDropdown")}, ${getDropdownValue(
      "provinceDropdown"
    )}`,
    shippingOption: getRadioValue("shippingOption"),
    paymentMethod: getRadioValue("paymentMethod"),
    totalAmount: document.getElementById("totalAmount").textContent,
    shippingCost: document.getElementById("shippingCost").textContent,
    finalTotal: document.getElementById("totalElement").textContent,
    couponID: document.getElementById("couponSelect") ? document.getElementById("couponSelect").value : ''
  };
}
function getSupplierData() {
  return {
    supplierID: getValue("SupplierID"),
    supplierName: getValue("SupplierName"),
    email: getValue("Email"),
    phone: getValue("Phone"),
    address: getValue("Address"),
  };
}
function getValue(name, type = "input") {
  const element = document.querySelector(`${type}[name="${name}"]`);
  return element ? element.value : "   ";
}

function getDropdownValue(dropdownID) {
  const dropdown = document.getElementById(dropdownID);
  return dropdown.options[dropdown.selectedIndex].value;
}
function getFileValue(name) {
  const fileInput = document.querySelector(`input[name="${name}"]`);
  return fileInput && fileInput.files.length > 0 ? fileInput.files[0] : null;
}
function getRadioValue(radioName) {
  const selected = document.querySelector(`input[name="${radioName}"]:checked`);
  return selected ? selected.value : null;
}
function notify(xhr, url, data) {
  let response = JSON.parse(xhr.responseText);
  if (xhr.readyState === 4) {
    if (xhr.status === 200) {
      if (response.message === "Success") {
        Swal.fire({
          icon: "success",
          title: "Thông báo.",
          text: response.message,
        }).then(() => {
          if (url !== "delete" && data.paymentMethod !== "Transfer")
            window.location.href = url;
        });
        if (url === "delete") {
          const $link = $(`a[onclick*='${data}']`);
          const currentStatus = $link.text().trim();
          // Đổi trạng thái giữa Active và Inactive
          if (currentStatus === "Active") {
            $link.html("Inactive");
          } else {
            $link.html("Active" + String.fromCharCode(160).repeat(3)); // Thêm 3 khoảng trắng
          }
        }
      } else {
        Swal.fire({
          icon: "error",
          title: "Thông báo.",
          text: response.message || "Thất bại",
        });
      }
    } else {
      Swal.fire({
        icon: "error",
        title: "Thông báo.",
        text: response.message,
      });
    }
  }
}
function sendRequest(method, endpoint1, endpoint2, data, url) {
  let xhr = new XMLHttpRequest();
  xhr.open(method, `http://localhost:8080/api/${endpoint1}/${endpoint2}`, true);
  if (endpoint1 !== "product" && endpoint2 !== "add") {
    xhr.setRequestHeader("Content-Type", "application/json");
  }

  const token = localStorage.getItem("token");
  xhr.setRequestHeader("Authorization", `Bearer ${token}`);

  xhr.onreadystatechange = function () {
    // Khi request hoàn thành và nhận được phản hồi
    if (xhr.readyState === XMLHttpRequest.DONE) {
      // Kiểm tra nếu token không hợp lệ (status 401 hoặc 403)
      if (xhr.status === 401 || xhr.status === 403) {
        extendToken(); // Gọi hàm extendToken để gia hạn token
      } else {
        // Nếu token hợp lệ, tiếp tục xử lý phản hồi
        notify(xhr, url, data);
      }
    }
  };

  if (endpoint1 !== "product" && endpoint2 !== "add") {
    xhr.send(JSON.stringify(data));
  } else {
    xhr.send(data);
  }
}

function getAccess(url) {
  fetch("/{url}", {
    method: "GET",
    headers: {
      Authorization: "Bearer " + localStorage.getItem("token"),
    },
  })
    .then((response) => {
      if (response.redirected) {
        window.location.href = url; // Chuyển hướng người dùng nếu server yêu cầu
      }
      return response.text(); // Nếu response trả về HTML
    })
    .then((html) => {
      document.open();
      document.write(html);
      document.close(); // Hiển thị trang HTML mới
    })
    .catch((error) => console.error("Error:", error));
}
function logout() {
  fetch("/api/auth/logout", {
    method: "POST",
    headers: {
      Authorization: "Bearer " + localStorage.getItem("token"),
    },
  })
    .then((response) => {
      if (response.ok) {
        localStorage.removeItem("token");

        Swal.fire({
          title: "Thành công!",
          text: "Bạn đã đăng xuất thành công.",
          icon: "success",
          confirmButtonText: "OK",
        }).then(() => {
          window.location.href = "/";
        });
      } else {
        Swal.fire({
          title: "Lỗi!",
          text: "Có lỗi xảy ra trong quá trình đăng xuất!",
          icon: "error",
          confirmButtonText: "OK",
        });
        localStorage.removeItem("token");
      }
    })
    .catch((error) => {
      console.error("Error:", error);

      Swal.fire({
        title: "Lỗi!",
        text: "Không thể kết nối đến máy chủ!",
        icon: "error",
        confirmButtonText: "OK",
      });
    });
}

//Token
function isTokenExpired(token) {
  if (token == null) {
    return true;
  }

  try {
    const payload = token.split(".")[1];

    const decodedPayload = JSON.parse(
      atob(payload.replace(/-/g, "+").replace(/_/g, "/"))
    );

    const currentTime = Date.now() / 1000; // Đổi sang giây
    return decodedPayload.exp < currentTime;
  } catch (error) {
    console.error("Token không hợp lệ:", error);
    return true;
  }
}
var token = localStorage.getItem("token");
if (isTokenExpired(token)) {
  localStorage.removeItem("token");
} else {
  // Nếu token còn hiệu lực, gửi yêu cầu AJAX
  $.ajax({
    url: "/", // API GET đến "/"
    method: "GET",
    headers: {
      Authorization: "Bearer " + token,
    },
    success: function (response) {
      var $responseHTML = $(response);
      var $newDropdownMenu = $responseHTML.find(".dropdown-menu").html();
      $("#dropdownContainer").html($newDropdownMenu);
    },
    error: function (xhr, status, error) {
      console.log("Error: " + error);
      localStorage.removeItem("token"); // Xóa token khi có lỗi
      window.location.href = "/login"; // Chuyển đến trang đăng nhập
    },
  });
}
