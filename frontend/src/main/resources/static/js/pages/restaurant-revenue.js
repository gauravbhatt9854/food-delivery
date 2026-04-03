// Simple restaurant revenue functionality
function handleRestaurantIdKeyPress(event) {
    if (event.key === 'Enter') {
        navigateToRestaurant();
    }
}

function navigateToRestaurant() {
    const restaurantIdInput = document.getElementById('restaurantIdInput');
    const fromDateInput = document.getElementById('fromDateInput');
    const toDateInput = document.getElementById('toDateInput');
    
    const restaurantId = restaurantIdInput.value.trim();
    const fromDate = fromDateInput.value;
    const toDate = toDateInput.value;
    
    // Validate that it's a positive number
    if (restaurantId && /^\d+$/.test(restaurantId) && parseInt(restaurantId) > 0) {
        let url = '/orders/revenue/restaurant/' + restaurantId;
        const params = new URLSearchParams();
        
        // Add date parameters if they exist
        if (fromDate) {
            params.append('fromDate', fromDate);
        }
        if (toDate) {
            params.append('toDate', toDate);
        }
        
        if (params.toString()) {
            url += '?' + params.toString();
        }
        
        window.location.href = url;
    } else {
        // Simple error feedback
        restaurantIdInput.style.borderColor = 'hsl(0, 70%, 50%)';
        setTimeout(() => {
            restaurantIdInput.style.borderColor = '';
        }, 2000);
    }
}

function applyDateFilter() {
    const restaurantIdInput = document.getElementById('restaurantIdInput');
    const fromDateInput = document.getElementById('fromDateInput');
    const toDateInput = document.getElementById('toDateInput');
    
    const restaurantId = restaurantIdInput.value.trim();
    const fromDate = fromDateInput.value;
    const toDate = toDateInput.value;
    
    if (restaurantId && /^\d+$/.test(restaurantId) && parseInt(restaurantId) > 0) {
        let url = '/orders/revenue/restaurant/' + restaurantId;
        const params = new URLSearchParams();
        
        if (fromDate) {
            params.append('fromDate', fromDate);
        }
        if (toDate) {
            params.append('toDate', toDate);
        }
        
        if (params.toString()) {
            url += '?' + params.toString();
        }
        
        window.location.href = url;
    } else {
        restaurantIdInput.style.borderColor = 'hsl(0, 70%, 50%)';
        setTimeout(() => {
            restaurantIdInput.style.borderColor = '';
        }, 2000);
    }
}

function clearDates() {
    const fromDateInput = document.getElementById('fromDateInput');
    const toDateInput = document.getElementById('toDateInput');
    
    fromDateInput.value = '';
    toDateInput.value = '';
    
    // Apply the cleared dates
    applyDateFilter();
}

// Extract restaurant ID and dates from URL and fill input boxes when page loads
document.addEventListener('DOMContentLoaded', function() {
    const currentPath = window.location.pathname;
    const urlParams = new URLSearchParams(window.location.search);
    
    // Extract restaurant ID from URL
    const restaurantIdMatch = currentPath.match(/\/orders\/revenue\/restaurant\/(\d+)/);
    if (restaurantIdMatch) {
        const restaurantId = restaurantIdMatch[1];
        const restaurantIdInput = document.getElementById('restaurantIdInput');
        if (restaurantIdInput) {
            restaurantIdInput.value = restaurantId;
        }
    }
    
    // Extract dates from query parameters
    const fromDate = urlParams.get('fromDate');
    const toDate = urlParams.get('toDate');
    
    const fromDateInput = document.getElementById('fromDateInput');
    const toDateInput = document.getElementById('toDateInput');
    
    if (fromDate && fromDateInput) {
        fromDateInput.value = fromDate;
    }
    if (toDate && toDateInput) {
        toDateInput.value = toDate;
    }
});
