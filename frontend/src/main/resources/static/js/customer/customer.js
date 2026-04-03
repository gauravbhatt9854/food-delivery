document.addEventListener("DOMContentLoaded", () => {

    const searchBtn = document.getElementById("searchBtn");
    const searchInput = document.getElementById("search");
    const pageSizeSelect = document.getElementById("pageSize");

    function getParams() {
        return new URLSearchParams(window.location.search);
    }

    function getBasePath() {
        return window.location.pathname;
    }

    // 🔍 SEARCH → always go to /customers/city
    searchBtn.addEventListener("click", () => {
        const city = searchInput.value.trim();

        if (!city) {
            alert("Enter city");
            return;
        }

        const params = new URLSearchParams();
        params.set("city", city);
        params.set("page", 0);
        params.set("size", pageSizeSelect.value || 5);

        window.location.href = `/customers/city?${params.toString()}`;
    });

    // ⌨️ ENTER SUPPORT
    searchInput.addEventListener("keypress", (e) => {
        if (e.key === "Enter") {
            searchBtn.click();
        }
    });

    // 📄 PAGE SIZE CHANGE
    pageSizeSelect.addEventListener("change", () => {

        const params = getParams();
        const basePath = getBasePath();

        // always update size + reset page
        params.set("size", pageSizeSelect.value);
        params.set("page", 0);

        // if current route is /customers/city → keep city
        if (basePath.includes("/customers/city")) {
            const city = params.get("city");
            if (city) {
                params.set("city", city);
            }
            window.location.href = `/customers/city?${params.toString()}`;
        } else {
            // normal customers route
            window.location.href = `/customers?${params.toString()}`;
        }
    });

});