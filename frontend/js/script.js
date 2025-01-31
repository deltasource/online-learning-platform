// Sample course data (in a real application, this would come from your Java Spring backend)
const featuredCourses = [
    {
        title: "Introduction to Web Development",
        instructor: "John Doe",
        price: 49.99,
        image: "https://via.placeholder.com/300x200.png?text=Web+Development"
    },
    {
        title: "Data Science Fundamentals",
        instructor: "Jane Smith",
        price: 59.99,
        image: "https://via.placeholder.com/300x200.png?text=Data+Science"
    },
    {
        title: "Digital Marketing Mastery",
        instructor: "Mike Johnson",
        price: 39.99,
        image: "https://via.placeholder.com/300x200.png?text=Digital+Marketing"
    }
];

// Function to create course cards
function createCourseCard(course) {
    return `
        <div class="col-md-4 mb-4">
            <div class="card featured-course-card">
                <img src="${course.image}" class="card-img-top" alt="${course.title}">
                <div class="card-body">
                    <h5 class="card-title">${course.title}</h5>
                    <p class="card-text">Instructor: ${course.instructor}</p>
                    <p class="card-text">Price: $${course.price}</p>
                    <a href="#" class="btn btn-primary">Enroll Now</a>
                </div>
            </div>
        </div>
    `;
}

// Function to load featured courses
function loadFeaturedCourses() {
    const coursesContainer = document.getElementById('featured-courses');
    featuredCourses.forEach(course => {
        coursesContainer.innerHTML += createCourseCard(course);
    });
}

// Load featured courses when the page is ready
document.addEventListener('DOMContentLoaded', loadFeaturedCourses);