document.addEventListener("DOMContentLoaded", () => {

    const search = document.getElementById("search");
    const pageSize = document.getElementById("pageSize");

    // 🔍 Search filter
    if (search) {
        search.addEventListener("keyup", function () {
            let value = this.value.toLowerCase();

            document.querySelectorAll("tbody tr").forEach(row => {
                row.style.display =
                    row.innerText.toLowerCase().includes(value) ? "" : "none";
            });
        });
    }

    // 📊 Page size change
    if (pageSize) {
        pageSize.addEventListener("change", function () {
            const size = this.value;
            window.location.href = `/customers?page=0&size=${size}`;
        });
    }

});