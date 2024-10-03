function sendAjaxRequest(method, url, data = null) {
  return new Promise((resolve, reject) => {
    const xhr = new XMLHttpRequest();
    xhr.open(method, url, true);

    // Thiết lập các tiêu đề
    const token = localStorage.getItem("token");
    if (token) {
      xhr.setRequestHeader("Authorization", `Bearer ${token}`);
    }
    xhr.setRequestHeader("Content-Type", "application/json");

    // Xử lý phản hồi từ server
    xhr.onload = function () {
      if (xhr.status >= 200 && xhr.status < 300) {
        resolve(xhr.responseText); // Trả về toàn bộ HTML
      } else {
        reject(new Error(`Yêu cầu không thành công: ${xhr.status}`));
      }
    };

    // Xử lý lỗi khi yêu cầu không thành công
    xhr.onerror = function () {
      reject(new Error("Lỗi kết nối đến server"));
    };

    // Gửi yêu cầu
    xhr.send(data ? JSON.stringify(data) : null);
  });
}
