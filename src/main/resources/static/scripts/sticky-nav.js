// When the user scrolls the page, execute myFunction 
window.onscroll = function() {myFunction()};

// Get the navbar
var navbar = document.getElementById("nav");
var filters = document.getElementById("filter-container");

// Get the offset position of the navbar
var sticky = navbar.offsetTop;

// Add the sticky class to the navbar when you reach its scroll position. Remove "sticky" when you leave the scroll position
function myFunction() {
  if (window.pageYOffset >= sticky) {
    navbar.classList.add("sticky-nav");
    // filters.classList.add("sticky-filters");
  } else {
    navbar.classList.remove("sticky-nav");
    // filters.classList.remove("sticky-filters");
  }
}