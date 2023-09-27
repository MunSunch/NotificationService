function handleErrors(response) {
    if(!response.ok) {
        response.json().then(data=>{
            alert(data['message']);
        });
    }
    return response;
}

let form = document.forms.my_form
form?.addEventListener('submit', (event)=>{
    event.preventDefault()

    let notificationDtoIn = {
        stationCode: form['stationCode'].value,
        pathOnStation: form['pathOnStation'].value,
        locomotive: {
            series: form['locomotive_series'].value,
            number: form['locomotive_number'].value
        },
        train: {
            numberTrain: form['train_number'].value,
            type: form['train_type'].value,
            weight: form['train_weight'].value,
            axes: form['train_axes'].value,
            units: form['train_units'].value,
            tail: form['train_tail'].value,
            onComing: form['train_oncoming'].value
        },
        parameters: {
            pressRequired: form['indicators_pressRequired'].value,
            pressActual: form['indicators_pressActual'].value,
            minPress: form['indicators_minPress'].value,
            handBrakesRequired: form['indicators_handBrakesRequired'].value,
            handBrakesActual: form['indicators_handBrakesActual'].value,
            brakeNetworkWithSecond: form['indicators_brakeNetworkWithSecond'].value,
            brakeNetworkWithFourth: form['indicators_brakeNetworkWithFourth'].value,
            brakeNetworkValue: form['indicators_brakeNetworkValue'].value,
            other: ['К-100', 'ТА']
        },
        timeLocomotiveTrailer: form['timeLocomotiveTrailer'].value,
        timeChargingBrakeNetwork: form['timeChargingBrakeNetwork'].value,
        timeIntegrityCheck: form['timeIntegrityCheck'].value,
        dateTimeFinish: form['timeFinish'].value,
        headEmployee: {
            name: form['notification_executors_head_name'].value,
            surname:form['notification_executors_head_surname'].value,
            patronymic:form['notification_executors_head_patronymic'].value
        },
        tailEmployee: {
            name: form['notification_executors_tail_name'].value,
            surname:form['notification_executors_tail_surname'].value,
            patronymic:form['notification_executors_tail_patronymic'].value
        },
        machinist: {
            name: form['notification_executors_machinist_name'].value,
            surname:form['notification_executors_machinist_surname'].value,
            patronymic:form['notification_executors_machinist_patronymic'].value
        }
    }

    fetch("http://localhost:9999/notifications/save", {
        method: 'POST',
        body: JSON.stringify(notificationDtoIn),
        headers: {
            'Content-type': 'application/json',
        }
    })
    .then(handleErrors)
    .then((response)=>{
        if(response.ok)
            alert('Успешно сохранено')
    });
})