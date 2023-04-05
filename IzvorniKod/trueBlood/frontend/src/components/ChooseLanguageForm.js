import React from 'react';
import {useHistory} from "react-router-dom";


function ChooseLanguageForm(props) {


    let chooseLanguageText, croatianLanguageText, englishLanguageText, confirmSelectionText;
    if (props.language === "croatian") {
        chooseLanguageText = "Odaberi jezik"
        croatianLanguageText = "Hrvatski"
        englishLanguageText = "Engleski"
        confirmSelectionText = "Potvrdi odabir"
    }

    if (props.language === "english") {
        chooseLanguageText = "Choose language"
        croatianLanguageText = "Croatian"
        englishLanguageText = "English"
        confirmSelectionText = "Confirm selection"
    }

    function onChange(event) {
        const {name, value} = event.target;
        props.languageSet(value)
    }


    return (

        <div className="ChooseLanguageForm">
                <p>{chooseLanguageText}</p>
                <input type="radio" id="croatian" name="language" value="croatian" onChange={onChange} checked={props.language === "croatian"}/>
                <label for="croatian">{croatianLanguageText}</label>
                <input type="radio" id="english" name="language" value="english" onChange={onChange} checked={props.language === "english"}/>
                <label for="english">{englishLanguageText}</label>
               
        </div>

    )
}

export default ChooseLanguageForm

