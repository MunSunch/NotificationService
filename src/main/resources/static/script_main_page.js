let params = (page, size) => '?page='+page+'&size='+size;
const size = 5;
let page = 0;

function handleErrors(response) {
    if(!response.ok) {
        response.json().then(data=>{
            alert(data['message']);
        });
    }
    return response;
}

fetch("http://localhost:9999/notifications/all"+params(page, size), {
    mode: 'cors',
    headers: {
        'Content-type': 'application/json',
    }
}).then((response) => {
    response.json().then(x => {
        for (let index = 0; index < x.length; index++) {
            createNotification(x[index]);
        }
    });
});

function createNotification(notif) {
    let bodyRef = document.getElementById('notifications_table').getElementsByTagName('tbody')[0];
    let row = bodyRef.insertRow();
    row.insertCell().appendChild(document.createTextNode(notif['id']));
    row.insertCell().appendChild(document.createTextNode(notif['stationCode']));
    row.insertCell().appendChild(document.createTextNode(notif['pathOnStation']));
    row.insertCell().appendChild(document.createTextNode(notif['locomotive']['locomotiveSeries']));
    row.insertCell().appendChild(document.createTextNode(notif['locomotive']['locomotiveNumber']));

    row.insertCell().appendChild(document.createTextNode(notif['train']['numberTrain']));
    row.insertCell().appendChild(document.createTextNode(notif['train']['type']));
    row.insertCell().appendChild(document.createTextNode(notif['train']['weight']));
    row.insertCell().appendChild(document.createTextNode(notif['train']['axes']));
    row.insertCell().appendChild(document.createTextNode(notif['train']['units']));
    row.insertCell().appendChild(document.createTextNode(notif['train']['tail']));
    row.insertCell().appendChild(document.createTextNode(notif['train']['oncoming']));

    row.insertCell().appendChild(document.createTextNode(notif['parameters']['pressRequired']));
    row.insertCell().appendChild(document.createTextNode(notif['parameters']['pressActual']));
    row.insertCell().appendChild(document.createTextNode(notif['parameters']['minPress']));
    row.insertCell().appendChild(document.createTextNode(notif['parameters']['handBrakesRequired']));
    row.insertCell().appendChild(document.createTextNode(notif['parameters']['handBrakesActual']));
    row.insertCell().appendChild(document.createTextNode(notif['parameters']['brakeNetworkWithSecond']));
    row.insertCell().appendChild(document.createTextNode(notif['parameters']['brakeNetworkWithFourth']));
    row.insertCell().appendChild(document.createTextNode(notif['parameters']['brakeNetworkValue']));

    row.insertCell().appendChild(document.createTextNode(notif['timeLocomotiveTrailer']));
    row.insertCell().appendChild(document.createTextNode(notif['timeChargingBrakeNetwork']));
    row.insertCell().appendChild(document.createTextNode(notif['timeIntegrityCheck']));
    row.insertCell().appendChild(document.createTextNode(notif['timeFinish']));
    row.insertCell().appendChild(document.createTextNode(notif['timeCreate']));

    row.insertCell().appendChild(document.createTextNode(notif['tailEmployee']['surname']+' '+notif['tailEmployee']['name']+' '+notif['tailEmployee']['patronymic']));
    row.insertCell().appendChild(document.createTextNode(notif['headEmployee']['surname']+' '+notif['headEmployee']['name']+' '+notif['headEmployee']['patronymic']));
    row.insertCell().appendChild(document.createTextNode(notif['machinist']['surname']+' '+notif['machinist']['name']+' '+notif['machinist']['patronymic']));
}

function lastPage() {
    if(page != 0)
        page -= 1;
    sendAndUpdateTable(page, size);
    setPage();
}

function nextPage() {
    page += 1;
    sendAndUpdateTable(page, size);
    setPage();
}

function setPage() {
    let btnPage = document.getElementById('page');
    btnPage.textContent = page;
}

function sendAndUpdateTable(page, size) {
    let findDtoIn = {
        numberTrain: form['find_number_train_input'].value,
        headEmployee: {
            name: form['find_head_employee_input_name'].value,
            surname: form['find_head_employee_input_surname'].value,
            patronymic: form['find_head_input_patronymic'].value
        },
        tailEmployee: {
            name: form['find_tail_employee_input_name'].value,
            surname: form['find_tail_employee_input_surname'].value,
            patronymic: form['find_tail_input_patronymic'].value
        },
        machinist: {
            name: form['find_machinist_input_name'].value,
            surname: form['find_machinist_input_surname'].value,
            patronymic: form['find_machinist_input_patronymic'].value
        },
        wagonTailNumber: form['find_number_wagon_tail_input'].value,
        wagonOncomingNumber: form['find_number_wagon_oncoming_input'].value,
        startPeriodDate: form['find_start_date_input'].value,
        endPeriodDate: form['find_end_date_input'].value
    };

    fetch("http://localhost:9999/notifications/find"+params(page, size), {
        method: 'POST',
        mode: 'cors',
        body: JSON.stringify(findDtoIn),
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(handleErrors)
    .then((response) => {
        let bodyRef = document.getElementById('notifications_table').getElementsByTagName('tbody')[0];
        bodyRef.innerHTML = '';

        response.json().then(x => {
            for (let index = 0; index < x.length; index++) {
                createNotification(x[index]);
            }
        })
    })
}

let form = document.forms.find_form;
form?.addEventListener('submit', (event) => {
    event.preventDefault();
    sendAndUpdateTable(page, size);
});