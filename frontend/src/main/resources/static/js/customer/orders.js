document.addEventListener("DOMContentLoaded", () => {

    const cards = document.querySelectorAll(".order-card");

    cards.forEach((card, i) => {
        card.style.opacity = "0";
        card.style.transform = "translateY(20px)";

        setTimeout(() => {
            card.style.transition = "0.3s ease";
            card.style.opacity = "1";
            card.style.transform = "translateY(0)";
        }, i * 100);
    });

});