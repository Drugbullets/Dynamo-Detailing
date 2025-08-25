// Dynamo Detailing - Main JavaScript File

document.addEventListener('DOMContentLoaded', function() {
    // Mobile menu toggle (if needed in future)
    const mobileMenuToggle = document.querySelector('.mobile-menu-toggle');
    const navMenu = document.querySelector('.nav-menu');
    
    if (mobileMenuToggle && navMenu) {
        mobileMenuToggle.addEventListener('click', function() {
            navMenu.classList.toggle('active');
        });
    }

    // Smooth scrolling for anchor links
    const anchorLinks = document.querySelectorAll('a[href^="#"]');
    anchorLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            const href = this.getAttribute('href');
            if (href !== '#' && document.querySelector(href)) {
                e.preventDefault();
                document.querySelector(href).scrollIntoView({
                    behavior: 'smooth'
                });
            }
        });
    });

    // Auto-hide alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.opacity = '0';
            setTimeout(() => {
                if (alert.parentNode) {
                    alert.parentNode.removeChild(alert);
                }
            }, 300);
        }, 5000);
    });

    // Form validation helpers
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            const requiredFields = form.querySelectorAll('[required]');
            let isValid = true;

            requiredFields.forEach(field => {
                if (!field.value.trim()) {
                    field.classList.add('is-invalid');
                    isValid = false;
                } else {
                    field.classList.remove('is-invalid');
                }
            });

            if (!isValid) {
                e.preventDefault();
                const firstInvalid = form.querySelector('.is-invalid');
                if (firstInvalid) {
                    firstInvalid.focus();
                    firstInvalid.scrollIntoView({ behavior: 'smooth', block: 'center' });
                }
            }
        });
    });

    // Real-time form validation
    const inputs = document.querySelectorAll('input, select, textarea');
    inputs.forEach(input => {
        input.addEventListener('blur', function() {
            if (this.hasAttribute('required') && !this.value.trim()) {
                this.classList.add('is-invalid');
            } else {
                this.classList.remove('is-invalid');
            }
        });

        input.addEventListener('input', function() {
            if (this.classList.contains('is-invalid') && this.value.trim()) {
                this.classList.remove('is-invalid');
            }
        });
    });

    // Price formatting
    const priceElements = document.querySelectorAll('.price, .service-price, [data-price]');
    priceElements.forEach(element => {
        const price = element.textContent || element.getAttribute('data-price');
        if (price && !isNaN(price)) {
            element.textContent = '$' + parseFloat(price).toFixed(2);
        }
    });

    // Table row highlighting
    const tableRows = document.querySelectorAll('table tbody tr');
    tableRows.forEach(row => {
        row.addEventListener('mouseenter', function() {
            this.style.backgroundColor = '#f8f9fa';
        });
        
        row.addEventListener('mouseleave', function() {
            this.style.backgroundColor = '';
        });
    });

    // Booking form service selection
    const serviceSelect = document.getElementById('service');
    if (serviceSelect) {
        serviceSelect.addEventListener('change', function() {
            const selectedOption = this.options[this.selectedIndex];
            const priceElement = document.getElementById('selectedPrice');
            const priceDisplay = document.getElementById('priceDisplay');
            
            if (this.value && selectedOption.dataset.price) {
                if (priceElement) {
                    priceElement.textContent = '$' + parseFloat(selectedOption.dataset.price).toFixed(2);
                }
                if (priceDisplay) {
                    priceDisplay.style.display = 'block';
                }
            } else {
                if (priceDisplay) {
                    priceDisplay.style.display = 'none';
                }
            }
        });
    }

    // Date input minimum date
    const dateInputs = document.querySelectorAll('input[type="date"]');
    dateInputs.forEach(input => {
        const tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        input.min = tomorrow.toISOString().split('T')[0];
    });

    // Loading states for buttons
    const submitButtons = document.querySelectorAll('button[type="submit"]');
    submitButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            const form = this.closest('form');
            if (form && form.checkValidity()) {
                this.style.opacity = '0.7';
                this.style.pointerEvents = 'none';
                
                const originalText = this.textContent;
                this.innerHTML = '<span class="loading"></span> Processing...';
                
                // Reset after 10 seconds (fallback)
                setTimeout(() => {
                    this.style.opacity = '';
                    this.style.pointerEvents = '';
                    this.textContent = originalText;
                }, 10000);
            }
        });
    });

    // Phone number formatting
    const phoneInputs = document.querySelectorAll('input[type="tel"]');
    phoneInputs.forEach(input => {
        input.addEventListener('input', function() {
            let value = this.value.replace(/\D/g, '');
            if (value.length >= 6) {
                value = value.replace(/(\d{3})(\d{3})(\d{4})/, '($1) $2-$3');
            } else if (value.length >= 3) {
                value = value.replace(/(\d{3})(\d{3})/, '($1) $2');
            }
            this.value = value;
        });
    });

    // Auto-capitalize first letter of text inputs
    const textInputs = document.querySelectorAll('input[type="text"]:not([data-no-capitalize])');
    textInputs.forEach(input => {
        input.addEventListener('blur', function() {
            if (this.value) {
                this.value = this.value.charAt(0).toUpperCase() + this.value.slice(1);
            }
        });
    });
});

// Utility functions
function showAlert(message, type = 'info') {
    const alert = document.createElement('div');
    alert.className = `alert alert-${type}`;
    alert.textContent = message;
    
    const container = document.querySelector('.container') || document.body;
    container.insertBefore(alert, container.firstChild);
    
    setTimeout(() => {
        alert.style.opacity = '0';
        setTimeout(() => {
            if (alert.parentNode) {
                alert.parentNode.removeChild(alert);
            }
        }, 300);
    }, 5000);
}

function formatCurrency(amount) {
    return '$' + parseFloat(amount).toFixed(2);
}

function formatPhoneNumber(phone) {
    const cleaned = phone.replace(/\D/g, '');
    if (cleaned.length === 10) {
        return '(' + cleaned.slice(0, 3) + ') ' + cleaned.slice(3, 6) + '-' + cleaned.slice(6);
    }
    return phone;
}
