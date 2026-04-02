document.addEventListener("DOMContentLoaded", () => {

    // Button loading state
    const buttons = document.querySelectorAll(".btn");

    buttons.forEach(btn => {
        btn.addEventListener("click", () => {
            btn.style.opacity = "0.7";
            btn.innerText = "Opening...";
        });
    });

    // Highlight rows on load (nice UX touch)
    const rows = document.querySelectorAll(".row");

    rows.forEach((row, i) => {
        setTimeout(() => {
            row.style.opacity = "1";
            row.style.transform = "translateY(0)";
        }, i * 100);
    });

});