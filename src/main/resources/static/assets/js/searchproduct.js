document.getElementById("searchInput").addEventListener("keypress", (event) => {
  if (event.key === "Enter") {
    updatePage();
  }
});

document.getElementById("searchButton").addEventListener("click", () => {
  updatePage();
});

function updatePage() {
  const input = document.getElementById("searchInput");
  const filter = input.value.trim().toLowerCase();

  const urlParams = new URLSearchParams(window.location.search);
  urlParams.set("search", filter);
  window.history.pushState({}, "", `?${urlParams.toString()}`);
  window.location.reload();
}

function getSearchQuery() {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get("search") || "";
}
