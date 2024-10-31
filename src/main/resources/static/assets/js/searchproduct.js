function filterProducts() {
  const input = document.getElementById("searchInput");
  const filter = input.value.toLowerCase(); // Chuyển đổi chuỗi tìm kiếm thành chữ thường
  const productList = document.getElementById("productList");
  const products = productList.getElementsByClassName("product-item");

  // Lặp qua từng sản phẩm để ẩn hoặc hiện chúng dựa trên tên
  for (let i = 0; i < products.length; i++) {
    const productName = products[i].getElementsByTagName("h4")[0].textContent; // Lấy tên sản phẩm
    if (productName.toLowerCase().includes(filter)) {
      products[i].style.display = ""; // Hiện sản phẩm
    } else {
      products[i].style.display = "none"; // Ẩn sản phẩm
    }
  }
}
