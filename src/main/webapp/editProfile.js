var topSelected = false
var bottomSelected = false

const itemsInForm = document.getElementById('user-info').getElementsByClassName('user_data')[0].getElementsByTagName('li')
const passwordButton = document.getElementById('password-button')
const editButton = document.getElementById('edit-button')
const passwordForm = document.getElementById('edit-password-div')

const cancelBtn = document.createElement('button')
cancelBtn.setAttribute('type', 'button')
cancelBtn.innerHTML = 'cancel';

function selectTop(){
    if(bottomSelected){
        deselectBottom()
    }

    for (var i = 0; i < itemsInForm.length; i++){
        const divElem = itemsInForm[i].firstElementChild
        const textDiv = divElem.getElementsByTagName('div')[0]
        const elemID = textDiv.getAttribute('id')

        const textField = createInputField('text', 'field-' + elemID, 'field-' + elemID, textDiv.textContent)

        textDiv.innerHTML = ''
        textDiv.appendChild(textField)
    }

    const editBtn = document.getElementById('edit-button')
    editBtn.remove()
    const saveBtn = createInputField('submit', '', 'save-button', 'save')
    cancelBtn.setAttribute('onmouseup', 'deselectTop()')

    const btnDiv = document.getElementById('btn-div')

    const ul = document.createElement('ul')

    const li1 = document.createElement('li')
    const li2 = document.createElement('li')

    li1.appendChild(saveBtn)
    li2.appendChild(cancelBtn)

    ul.appendChild(li1)
    ul.appendChild(li2)
    btnDiv.appendChild(ul)

    btnDiv.appendChild(ul)

    topSelected = true
}

function selectBottom(){
    if(topSelected){
        deselectTop()
    }

    passwordButton.style.visibility = 'hidden'
    passwordForm.style.visibility = 'visible'

    bottomSelected = true
}

function deselectTop(){
    for (var i = 0; i < itemsInForm.length; i++){
        const divElem = itemsInForm[i].firstElementChild
        const textDiv = divElem.getElementsByTagName('div')[0]
        const elemID = textDiv.getAttribute('id')

        const oldValue = document.getElementById('hidden-' + elemID).getAttribute('value')

        textDiv.innerHTML = oldValue
    }

    const btnDiv = document.getElementById('btn-div')
    btnDiv.firstElementChild.remove()
    btnDiv.appendChild(editButton)
    topSelected = false
}

function deselectBottom(){
    passwordForm.style.visibility = 'hidden'
    passwordButton.style.visibility = 'visible'

    for(var i = 0; i < passwordForm.getElementsByClassName('pass-input').length; i++){
        passwordForm.getElementsByClassName('pass-input')[i].value = ''
    }

    bottomSelected = false
}

function createInputField(type, name, id, value){
    const field = document.createElement('input')
    if(name != ''){
        field.setAttribute('name', name)
    }
    if(type != ''){
        field.setAttribute('type', type)
    }
    if(id != ''){
        field.setAttribute('id', id)
    }
    if(value != ''){
        field.setAttribute('value', value)
    }
    return field
}