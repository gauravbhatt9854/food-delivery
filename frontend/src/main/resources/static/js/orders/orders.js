
function goPage(pageNumber) {
    const currentUrl = new URL(window.location);
    
    const serverPage = pageNumber;
    
    const rowsPerPage = document.getElementById('rows-select').value || 5;
    
    currentUrl.searchParams.set('page', serverPage);
    currentUrl.searchParams.set('size', rowsPerPage);
    
    window.location.href = currentUrl.toString();
}

function statusFilterChange(event) {
    let status = (event.target.value);

    const url = new URL(window.location.href);

    if (status === "Delivered") {
        url.searchParams.set('status', 'Delivered');
    } else if (status === "Pending") {
        url.searchParams.set('status', 'Pending');
    } else {
        url.searchParams.delete('status');
    }
    url.searchParams.set('page', 0);

    localStorage.setItem("status", status);

    window.location.href = url.toString();
}

function sortFilterChange(event) {
    let sort = (event.target.value);

    const url = new URL(window.location.href);

    if (sort === "") {
        url.searchParams.delete('sort');
    } else {
        url.searchParams.set('sort', sort);
    }

    url.searchParams.set('page', 0);

    localStorage.setItem("sort", sort);

    window.location.href = url.toString();

}

function searchFilterChange(event) {
    const searchInput = event.target;
    const input = searchInput.value.trim().toLowerCase();
    if (input.length <= 2) {
        const table = document.getElementById('orders-table');
        const rows = Array.from(table.querySelectorAll('tbody tr'));
        rows.forEach(row => {
            row.style.display = '';
        });
    } else {
        const table = document.getElementById('orders-table');
        const rows = Array.from(table.querySelectorAll('tbody tr'));
        rows.forEach(row => {
            const itemName = row.querySelector('.item-name').textContent.trim().toLowerCase();
            const restaurantName = row.querySelector('.restaurant-name').textContent.trim().toLowerCase();
            if (itemName.includes(input) || restaurantName.includes(input)) {
                row.style.display = '';
            } else {
                row.style.display = 'none';
            }
        });
    }
}

function openLookup() {
    const lookupBar = document.getElementById('lookup-bar');
    const triggerBtn = document.getElementById('lookup-trigger-btn');
    const lookupInput = document.getElementById('lookup-input');
    
    // Show the lookup bar using CSS class
    lookupBar.classList.add('open');
    
    // Hide the trigger button
    triggerBtn.style.display = 'none';
    
    // Focus on the input
    lookupInput.focus();
}

function closeLookup() {
    const lookupBar = document.getElementById('lookup-bar');
    const triggerBtn = document.getElementById('lookup-trigger-btn');
    const lookupInput = document.getElementById('lookup-input');
    
    // Hide the lookup bar using CSS class
    lookupBar.classList.remove('open');
    
    // Show the trigger button
    triggerBtn.style.display = 'flex';
    
    // Clear and enable input
    lookupInput.value = '';
    lookupInput.disabled = false;
}

function submitLookup() {
    const lookupInput = document.getElementById('lookup-input');
    const input = lookupInput.value.trim();
    
    if (!input) {
        alert('Please enter a customer ID');
        return;
    }
    
    // Validate that input is a number
    if (isNaN(input) || input <= 0) {
        alert('Please enter a valid customer ID (positive number)');
        return;
    }
    
    // Reset UI state before redirect
    const lookupBar = document.getElementById('lookup-bar');
    const triggerBtn = document.getElementById('lookup-trigger-btn');
    if (lookupBar && triggerBtn) {
        lookupBar.classList.remove('open');
        triggerBtn.style.display = 'flex';
    }
    
    // Redirect to the customer orders page
    window.location.href = '/orders/customer/' + encodeURIComponent(input);
}



document.addEventListener('DOMContentLoaded', function() {
    const rowsSelect = document.getElementById('rows-select');
    if (rowsSelect) {
        rowsSelect.addEventListener('change', function() {
            goPage(0);
        });
    }
});

document.addEventListener('DOMContentLoaded', function() {
    const status = localStorage.getItem("status");
    const sort = localStorage.getItem("sort");
    if (status) {
        document.getElementById('status-filter').value = status;
    }
    if (sort) {
        document.getElementById('sort-filter').value = sort;
    }
});

document.addEventListener('DOMContentLoaded', function() {
    // Initialize lookup bar state - ensure trigger button is visible and lookup bar is closed
    const lookupBar = document.getElementById('lookup-bar');
    const triggerBtn = document.getElementById('lookup-trigger-btn');
    
    if (lookupBar && triggerBtn) {
        lookupBar.classList.remove('open');
        triggerBtn.style.display = 'flex';
    }
});

