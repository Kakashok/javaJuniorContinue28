function deleteStudents(){
    var checkedCheckboxs = document.querySelectorAll('input[name=idStudent]:checked')

    if (checkedCheckboxs.length == 0){
        alert("Пожалуйста, выберете хотя-бы лдного студента");
        return;
    }

    var ids = "";
    for (var i = 0; i < checkedCheckboxs.length; i++){
        ids = ids + checkedCheckboxs[i].value + " "

    }

    document.getElementById("idsHiddenDelete").value = ids;
    document.getElementById("deleteForm").submit();
}

function modifyStudent(){
    var checkedCheckboxs = document.querySelectorAll('input[name=idStudent]:checked')

    if (checkedCheckboxs.length == 0){
        alert("Пожалуйста, выберете одного студента");
        return;
    }
    if (checkedCheckboxs.length > 1){
        alert("Пожалуйста, выберете ТОЛЬКО одного студента");
        return;
    }

    var id = checkedCheckboxs[0].value;


    document.getElementById("idsHiddenModify").value = id;
    document.getElementById("modifyForm").submit();
}

function progressStudent(){
    var checkedCheckboxs = document.querySelectorAll('input[name=idStudent]:checked')

    if (checkedCheckboxs.length == 0){
        alert("Пожалуйста, выберете одного студента");
        return;
    }
    if (checkedCheckboxs.length > 1){
        alert("Пожалуйста, выберете ТОЛЬКО одного студента");
        return;
    }

    var id = checkedCheckboxs[0].value;


    document.getElementById("idHiddenProgress").value = id;
    document.getElementById("progressForm").submit();
}