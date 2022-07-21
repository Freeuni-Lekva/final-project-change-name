var topSelected = false
var bottomSelected = false

const itemsInForm = document.getElementById('user-info').getElementsByTagName('li')
const passwordButton = document.getElementById('password-button')
const editButton = document.getElementById('edit-button')
const passwordForm = createPasswordForm()


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
    const saveBtn = createInputField('submit', '', 'save-button', 'save')
    editBtn.replaceWith(saveBtn)

    topSelected = true
}

function selectBottom(){
    if(topSelected){
        deselectTop()
    }

    passwordButton.replaceWith(passwordForm)

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

    const saveBtn = document.getElementById('save-button')
    saveBtn.replaceWith(editButton)
    topSelected = false
}

function deselectBottom(){
    passwordForm.replaceWith(passwordButton)

    bottomSelected = false
}


function createPasswordForm(){
    const divElem = document.createElement('div')
    divElem.setAttribute('id', 'edit-password-div')
    const form = document.createElement('form')

    form.setAttribute('action', 'EditPasswordServlet')
    form.setAttribute('method', 'post')

    const curPassword = createInputField('password', 'currentPassword', 'cur-password', '')
    const newPassword = createInputField('password', 'newPassword', 'new-password', '')

    const curPasswordLabel = createLabelFor('cur-password', 'Current Password: ')
    const newPasswordLabel = createLabelFor('new-password', 'New Password: ')

    const saveButton = createInputField('submit', '', '', 'save')

    form.appendChild(saveButton)

    const breakLine1 = document.createElement('br')

    form.appendChild(breakLine1)

    form.appendChild(curPasswordLabel)
    form.appendChild(curPassword)

    const breakLine = document.createElement('br')

    form.appendChild(breakLine)

    form.appendChild(newPasswordLabel)
    form.appendChild(newPassword)

    divElem.appendChild(form)

    return divElem
}

function createLabelFor(forID, text){
    const label = document.createElement('label')
    label.setAttribute('for', forID)
    label.innerHTML = text;
    return label
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