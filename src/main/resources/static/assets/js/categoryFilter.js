// Lấy tất cả các liên kết danh mục
const categoryLinks = document.querySelectorAll(".category-link");

// Lắng nghe sự kiện click trên các liên kết danh mục
categoryLinks.forEach((link) => {
  link.addEventListener("click", (event) => {
    event.preventDefault();

    // Lấy ID của danh mục được chọn
    const categoryId = event.currentTarget.dataset.categoryId;
    if (categoryId) {
      console.log(categoryId);
      // Hiển thị các sản phẩm tương ứng với danh mục
      displayProductsByCategory(categoryId);
    } else {
      console.log("categoryId not found");
      displayProductsByCategory(null);
    }
  });
});

function displayProductsByCategory(categoryId) {
  // Ẩn tất cả các sản phẩm
  const listItems = document.querySelectorAll(".list-item");
  listItems.forEach((item) => {
    item.style.display = "none";
  });

  // Hiển thị các sản phẩm thuộc danh mục được chọn
  if (categoryId === "all") {
    listItems.forEach((item) => {
      item.style.display = "block";
    });
  } else if (categoryId) {
    const productsByCategory = document.querySelectorAll(
      `.list-item[data-category-id="${categoryId}"]`
    );
    productsByCategory.forEach((product) => {
      product.style.display = "block";
    });
  } else {
    // Nếu categoryId là null, hiển thị toàn bộ danh sách
    listItems.forEach((item) => {
      item.style.display = "block";
    });
  }
}
