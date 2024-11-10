const categoryLinks = document.querySelectorAll(".category-link");

categoryLinks.forEach((link) => {
  link.addEventListener("click", (event) => {
    event.preventDefault();

    const categoryId = event.currentTarget.dataset.categoryId;
    if (categoryId) {
      if (categoryId == "all") {
        displayProductsByCategoryAll();
      }
      console.log("Selected category ID: ", categoryId);
      displayProductsByCategory(categoryId);
    } else {
      displayProductsByCategory(null);
    }
  });
});

function displayProductsByCategory(categoryId) {
  fetch(`/search?categoryId=${categoryId}&page=0&size=10`)
    .then((response) => response.json())
    .then((data) => {
      const products = data.content;
      displayResults(products);
    })
    .catch((error) => {
      console.error("Error fetching products: ", error);
    });
}

function displayProductsByCategoryAll() {
  fetch("/search")
    .then((response) => response.json())
    .then((data) => {
      const products = data.content;
      displayResults(products);
    })
    .catch((error) => {
      console.error("Error fetching products: ", error);
    });
}
function formatPrice(price) {
  const formattedPrice = price.toLocaleString("en-US");

  return formattedPrice;
}
function displayResults(products) {
  const productList = document.getElementById("productList");
  productList.innerHTML = "";

  const row = document.createElement("div");
  row.classList.add("row");

  for (let i = 0; i < 8; i++) {
    const product = products[i];
    const productItem = document.createElement("div");
    productItem.classList.add("col-md-3");

    const formattedPrice = new Intl.NumberFormat("vi-VN").format(
      product.unitPrice
    );

    productItem.innerHTML = `
      <div class="rounded position-relative fruite-item">
        <div class="fruite-img">
          <a href="/detail/${product.productID}">
            <img src="${product.image.imageCode}" alt="${
      product.productName
    }" class="img-fluid w-100 rounded-top" style="height: 240px">
          </a>
        </div>
        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute" style="top: 10px; left: 10px">
          ${product.category ? product.category.categoryName : "N/A"}
        </div>
        <div class="p-4 border border-secondary border-top-0 rounded-bottom">
          <a href="/detail/${product.productID}">
            <h4 style="
                display: -webkit-box;
                -webkit-line-clamp: 4;
                -webkit-box-orient: vertical;
                overflow: hidden;
                min-height: 115px;
              ">${product.productName}</h4>
          </a>
          <br>
          <div class="d-flex justify-content-start flex-lg-wrap">
            <p class="text-dark fs-5 fw-bold mb-0 pe-lg-3" style="height: 46px">
            
            ${formatPrice(product.unitPrice)}
             VND</p>
          </div>
          <br>
          <div class="d-flex justify-content-end">
            <a class="btn border border-secondary rounded-pill px-3 text-primary" onclick="addToCart('${
              product.productID
            }')">
              <i class="fa fa-shopping-bag me-2 text-primary"></i>
              Giỏ hàng
            </a>
          </div>
        </div>
      </div>
    `;

    row.appendChild(productItem);
  }

  // Gắn row vào productList
  productList.appendChild(row);
}
