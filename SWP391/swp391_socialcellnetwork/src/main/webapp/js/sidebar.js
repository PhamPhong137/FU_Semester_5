// js for add event
const calendar_type = document.getElementById('calendar_type');
const solarDateInput = document.getElementById('solar');
const lunarDateInput = document.getElementById('lunar');
const showSolarFromLunar = document.getElementById('changeToSolar');
const lunartosolar = document.getElementById('lunartosolar');
const lunar_date = document.getElementById('lunar_date');
const repeat = document.getElementById('repeat');
const event_title = document.getElementById('event-title');

showSolarFromLunar.style.display = 'none';
lunarDateInput.style.display = 'none';

const changeCalendar = () => {
    lunarDateInput.style.display = calendar_type.value == 1 ? 'block' : 'none';
    solarDateInput.style.display = calendar_type.value == 0 ? 'block' : 'none';
    showSolarFromLunar.style.display = calendar_type.value == 1 ? 'block' : 'none';
    solarDateInput.value = lunarDateInput.value = '';
    lunartosolar.textContent = '';
};
const convertToSolar = () => {
    const datePattern = /^\d{2}\/\d{2}\/\d{4}$/;
    if (!datePattern.test(lunarDateInput.value)) {
        lunartosolar.textContent = "Please enter the correct format";
        lunartosolar.style.color = "red";
    } else {
        const lunarParts = lunarDateInput.value.split('/');

        const day = parseInt(lunarParts[0]);
        const month = parseInt(lunarParts[1]);
        const year = parseInt(lunarParts[2]);

        const solarDateArray = convertLunar2Solar(day, month, year, 0, 7);
        console.log(lunarParts);
        const formattedSolarDateArray = solarDateArray.map(num => String(num).padStart(2, '0'));
        const formattedLunarDate = lunarParts.map(num => String(num).padStart(2, '0'));
        lunar_date.value = formattedLunarDate.reverse().join("-");
        lunartosolar.textContent = formattedSolarDateArray.join("/");
        lunartosolar.style.color = "green";
    }
};

function chooseFile(fileInput) {
    if (fileInput.files && fileInput.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#image').attr('src', e.target.result);
            // Save the image data to local storage only when a file is chosen
            localStorage.setItem('imageData', e.target.result);
        },
                reader.readAsDataURL(fileInput.files[0]);
    }
}
;
function validateForm() {
    var solarDate = document.getElementById("solar").value;
    var lunarDate = document.getElementById("lunar").value;
    if (solarDate === "" && lunarDate === "") {
        alert("You must fill the date");
        return false;
    }
    return true;
}
;
function fillTemplate() {
    const templateId = document.getElementById('template');

    if (templateId.value == 2) {
        event_title.value = "Memorial day";
        calendar_type.value = 1;
        changeCalendar();
        repeat.value = 0;
    } else if (templateId.value == 3) {
        event_title.value = "Longevity ceremony";
        calendar_type.value = 1;
        changeCalendar();
        repeat.value = 1;
    } else if (templateId.value == 4) {
        event_title.value = "Happy wedding";
        calendar_type.value = 0;
        changeCalendar();
        repeat.value = 1;
    } else if (templateId.value == 5) {
        event_title.value = "Happy birthday";
        calendar_type.value = 0;
        changeCalendar();
        repeat.value = 1;
    } else {
        event_title.value = "";
        calendar_type.value = 0;
        changeCalendar();
        repeat.value = 1;
    }
}





