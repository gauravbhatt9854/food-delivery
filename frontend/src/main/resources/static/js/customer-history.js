// Professional Customer History Table Interactions

class CustomerHistoryManager {
    constructor() {
        this.init();
        this.attachEventListeners();
        this.initializeAnimations();
    }

    init() {
        console.log('Customer History Manager initialized');
        this.setupTableInteractions();
        this.initializeFilters();
        this.setupKeyboardNavigation();
    }

    attachEventListeners() {
        // Filter buttons
        document.querySelectorAll('.filter-btn').forEach(btn => {
            btn.addEventListener('click', (e) => this.handleFilterClick(e));
        });

        // Table row interactions
        document.querySelectorAll('.orders-table tbody tr').forEach(row => {
            row.addEventListener('click', (e) => this.handleRowClick(e));
            row.addEventListener('mouseenter', (e) => this.handleRowHover(e, true));
            row.addEventListener('mouseleave', (e) => this.handleRowHover(e, false));
        });

        // Summary cards interactions
        document.querySelectorAll('.summary-card').forEach(card => {
            card.addEventListener('click', (e) => this.handleSummaryCardClick(e));
        });

        // Window resize for responsive behavior
        window.addEventListener('resize', this.debounce(() => this.handleResize(), 250));

        // Keyboard navigation
        document.addEventListener('keydown', (e) => this.handleKeyboardNavigation(e));
    }

    initializeAnimations() {
        // Add fade-in animation to elements
        const observerOptions = {
            threshold: 0.1,
            rootMargin: '0px 0px -50px 0px'
        };

        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('fade-in');
                    observer.unobserve(entry.target);
                }
            });
        }, observerOptions);

        // Observe elements for animation
        document.querySelectorAll('.summary-card, .table-container').forEach(el => {
            observer.observe(el);
        });
    }

    setupTableInteractions() {
        const table = document.querySelector('.orders-table');
        if (!table) return;

        // Add row numbering
        const rows = table.querySelectorAll('tbody tr');
        rows.forEach((row, index) => {
            const firstCell = row.querySelector('td');
            if (firstCell && !firstCell.querySelector('.row-number')) {
                const rowNumber = document.createElement('span');
                rowNumber.className = 'row-number';
                rowNumber.textContent = `${index + 1}.`;
                rowNumber.style.cssText = 'color: var(--medium-gray); font-size: 0.8rem; margin-right: 0.5rem;';
                firstCell.insertBefore(rowNumber, firstCell.firstChild);
            }
        });

        // Add sorting indicators
        this.addSortingCapabilities();
    }

    addSortingCapabilities() {
        const headers = document.querySelectorAll('.orders-table th');
        headers.forEach(header => {
            if (!header.querySelector('.sort-indicator')) {
                const sortIndicator = document.createElement('i');
                sortIndicator.className = 'fas fa-sort sort-indicator';
                sortIndicator.style.cssText = 'margin-left: 0.5rem; opacity: 0.3; font-size: 0.8rem;';
                header.appendChild(sortIndicator);
                header.style.cursor = 'pointer';
                header.addEventListener('click', () => this.sortTable(header));
            }
        });
    }

    sortTable(header) {
        const table = document.querySelector('.orders-table');
        const tbody = table.querySelector('tbody');
        const rows = Array.from(tbody.querySelectorAll('tr'));
        const columnIndex = Array.from(header.parentNode.children).indexOf(header);
        const isAscending = !header.classList.contains('sort-asc');

        // Update sort indicators
        document.querySelectorAll('.orders-table th').forEach(th => {
            th.classList.remove('sort-asc', 'sort-desc');
            const indicator = th.querySelector('.sort-indicator');
            if (indicator) {
                indicator.className = 'fas fa-sort sort-indicator';
                indicator.style.opacity = '0.3';
            }
        });

        header.classList.add(isAscending ? 'sort-asc' : 'sort-desc');
        const indicator = header.querySelector('.sort-indicator');
        if (indicator) {
            indicator.className = `fas fa-sort-${isAscending ? 'up' : 'down'} sort-indicator`;
            indicator.style.opacity = '1';
        }

        // Sort rows
        rows.sort((a, b) => {
            const aValue = a.children[columnIndex].textContent.trim();
            const bValue = b.children[columnIndex].textContent.trim();
            
            // Try to parse as numbers for price/quantity columns
            const aNum = parseFloat(aValue.replace(/[^0-9.-]+/g, ''));
            const bNum = parseFloat(bValue.replace(/[^0-9.-]+/g, ''));
            
            if (!isNaN(aNum) && !isNaN(bNum)) {
                return isAscending ? aNum - bNum : bNum - aNum;
            }
            
            // String comparison
            return isAscending ? 
                aValue.localeCompare(bValue) : 
                bValue.localeCompare(aValue);
        });

        // Re-append sorted rows with animation
        rows.forEach((row, index) => {
            setTimeout(() => {
                tbody.appendChild(row);
                row.classList.add('fade-in');
            }, index * 50);
        });
    }

    initializeFilters() {
        this.currentFilter = 'all';
        this.originalRows = Array.from(document.querySelectorAll('.orders-table tbody tr'));
    }

    handleFilterClick(e) {
        const button = e.currentTarget;
        const filter = button.textContent.trim().toLowerCase();
        
        // Update active state
        document.querySelectorAll('.filter-btn').forEach(btn => {
            btn.classList.remove('active');
        });
        button.classList.add('active');

        // Apply filter
        this.applyFilter(filter);
    }

    applyFilter(filter) {
        const tbody = document.querySelector('.orders-table tbody');
        const rows = this.originalRows;

        rows.forEach(row => {
            const statusCell = row.querySelector('.status-badge');
            const status = statusCell ? statusCell.textContent.trim().toLowerCase() : '';
            
            let shouldShow = filter === 'all' || status === filter;
            
            if (shouldShow) {
                row.style.display = '';
                row.classList.add('fade-in');
            } else {
                row.style.display = 'none';
            }
        });

        // Update empty state
        this.updateEmptyState();
    }

    updateEmptyState() {
        const visibleRows = document.querySelectorAll('.orders-table tbody tr:not([style*="display: none"])');
        const emptyState = document.querySelector('.empty-state');
        
        if (visibleRows.length === 0 && emptyState) {
            emptyState.style.display = 'block';
        } else if (emptyState) {
            emptyState.style.display = 'none';
        }
    }

    handleRowClick(e) {
        const row = e.currentTarget;
        const rowData = this.extractRowData(row);
        
        // You can add row click actions here
        console.log('Row clicked:', rowData);
        
        // Add selection effect
        document.querySelectorAll('.orders-table tbody tr').forEach(r => {
            r.classList.remove('selected');
        });
        row.classList.add('selected');
    }

    handleRowHover(e, isEntering) {
        const row = e.currentTarget;
        if (isEntering) {
            row.style.transform = 'scale(1.01)';
            row.style.boxShadow = 'var(--shadow-sm)';
        } else {
            row.style.transform = '';
            row.style.boxShadow = '';
        }
    }

    handleSummaryCardClick(e) {
        const card = e.currentTarget;
        const value = card.querySelector('.summary-value').textContent;
        const label = card.querySelector('.summary-label').textContent;
        
        console.log(`${label}: ${value}`);
        
        // Add pulse animation
        card.style.animation = 'pulse 0.3s ease-in-out';
        setTimeout(() => {
            card.style.animation = '';
        }, 300);
    }

    handleResize() {
        // Responsive adjustments
        const isMobile = window.innerWidth < 768;
        const table = document.querySelector('.orders-table');
        
        if (table) {
            if (isMobile) {
                table.classList.add('mobile-view');
                this.optimizeForMobile();
            } else {
                table.classList.remove('mobile-view');
                this.restoreDesktopView();
            }
        }
    }

    optimizeForMobile() {
        // Add mobile-specific optimizations
        const headers = document.querySelectorAll('.orders-table th');
        headers.forEach(header => {
            if (header.textContent.includes('Restaurant')) {
                header.style.display = 'none';
            }
        });
        
        const restaurantCells = document.querySelectorAll('.restaurant-cell');
        restaurantCells.forEach(cell => {
            cell.style.display = 'none';
        });
    }

    restoreDesktopView() {
        // Restore desktop view
        const headers = document.querySelectorAll('.orders-table th');
        headers.forEach(header => {
            header.style.display = '';
        });
        
        const cells = document.querySelectorAll('.orders-table td');
        cells.forEach(cell => {
            cell.style.display = '';
        });
    }

    setupKeyboardNavigation() {
        this.focusedRowIndex = -1;
        this.rows = Array.from(document.querySelectorAll('.orders-table tbody tr'));
    }

    handleKeyboardNavigation(e) {
        if (e.target.closest('.filter-buttons') || e.target.closest('input')) return;

        switch(e.key) {
            case 'ArrowDown':
                e.preventDefault();
                this.focusNextRow();
                break;
            case 'ArrowUp':
                e.preventDefault();
                this.focusPreviousRow();
                break;
            case 'Enter':
                e.preventDefault();
                this.selectFocusedRow();
                break;
        }
    }

    focusNextRow() {
        if (this.focusedRowIndex < this.rows.length - 1) {
            this.focusedRowIndex++;
            this.updateRowFocus();
        }
    }

    focusPreviousRow() {
        if (this.focusedRowIndex > 0) {
            this.focusedRowIndex--;
            this.updateRowFocus();
        }
    }

    updateRowFocus() {
        this.rows.forEach((row, index) => {
            row.classList.remove('keyboard-focused');
            if (index === this.focusedRowIndex) {
                row.classList.add('keyboard-focused');
                row.scrollIntoView({ behavior: 'smooth', block: 'center' });
            }
        });
    }

    selectFocusedRow() {
        if (this.focusedRowIndex >= 0 && this.focusedRowIndex < this.rows.length) {
            this.rows[this.focusedRowIndex].click();
        }
    }

    extractRowData(row) {
        const cells = row.querySelectorAll('td');
        return {
            date: cells[0]?.textContent.trim(),
            item: cells[1]?.textContent.trim(),
            restaurant: cells[2]?.textContent.trim(),
            quantity: cells[3]?.textContent.trim(),
            price: cells[4]?.textContent.trim(),
            total: cells[5]?.textContent.trim(),
            status: cells[6]?.textContent.trim()
        };
    }

    // Utility function for debouncing
    debounce(func, wait) {
        let timeout;
        return function executedFunction(...args) {
            const later = () => {
                clearTimeout(timeout);
                func(...args);
            };
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
        };
    }

    // Public methods for external use
    refreshData() {
        // Refresh table data
        console.log('Refreshing data...');
        this.init();
    }

    exportToCSV() {
        const table = document.querySelector('.orders-table');
        const rows = table.querySelectorAll('tr');
        let csv = [];

        rows.forEach(row => {
            const rowData = [];
            const cells = row.querySelectorAll('th, td');
            cells.forEach(cell => {
                rowData.push(cell.textContent.trim());
            });
            csv.push(rowData.join(','));
        });

        return csv.join('\n');
    }

    printTable() {
        window.print();
    }
}

// Add CSS for keyboard focus
const style = document.createElement('style');
style.textContent = `
    .keyboard-focused {
        outline: 2px solid var(--secondary-color) !important;
        outline-offset: -2px;
        background: linear-gradient(135deg, #f8f9ff 0%, #f0f3ff 100%) !important;
    }
    
    .selected {
        background: linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%) !important;
    }
    
    @keyframes pulse {
        0% { transform: scale(1); }
        50% { transform: scale(1.05); }
        100% { transform: scale(1); }
    }
    
    @media print {
        .filter-buttons, .customer-header {
            display: none !important;
        }
        
        .table-container {
            box-shadow: none !important;
            border: 1px solid #ddd !important;
        }
    }
`;
document.head.appendChild(style);

// Initialize the manager when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    window.customerHistoryManager = new CustomerHistoryManager();
});

// Export for module systems
if (typeof module !== 'undefined' && module.exports) {
    module.exports = CustomerHistoryManager;
}
