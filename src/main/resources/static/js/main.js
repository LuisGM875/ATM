function addToAmount(value) {
    const amountField = document.getElementById('amount');
    const dotButton = document.getElementById('dotButton');

    if (value === '.' && amountField.value.includes('.')) {
        return;
    }
    if (isNaN(value) && value !== '.') {
        return;
    }
    if (amountField.value === "0" && value === '.') {
        amountField.value = "0.";
    } else if (amountField.value === "0" && value !== '0') {
        amountField.value = value;
    } else {
        amountField.value += value;
    }

    dotButton.disabled = !(amountField.value !== "0" && amountField.value !== "");
}

function clearAmount() {
    document.getElementById('amount').value = '0';
}