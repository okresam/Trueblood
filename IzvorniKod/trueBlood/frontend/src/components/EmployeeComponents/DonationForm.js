import React, { useEffect, useState } from 'react';
import { Form, Row, Col, Button } from 'react-bootstrap';
import axios from "axios";
import {SPRING_URL} from '../Constants';

function DonationForm(props) {

    const questionsList = [
        'Težina je ispod 55kg?',
        'Temperatura je iznad 37c?',
        'Krvni tlak: sistolični ispod 100 ili iznad 180 mm Hg, dijastolični ispod 60 ili iznad 110 mm Hg?',
        'Puls: ispod 50 ili iznad 100 otkucaja u minuti', 
        'Hemoglobin: muškarci ispod 135 g/L, žene ispod 125 g/L',
        'Osoba trenutno uzima antibiotike ili druge lijekove',
        'Osoba je konzumirale alkoholna pića unutar 8 sati prije darivanja krvi',
        'Osoba s lakšim akutnim bolesnim stanjima (prehlade, poremetnje probavnog sustava, smanjenog željeza u krvi i sl.)',
        'Žene za vrijeme menstruacije, trudnoće i dojenja',
        'Osoba tog dana obavlja opasne poslove (rad na visini, dubini)',
        'Osoba je bolovale ili sada boluju od teških kroničnih bolesti dišnog i probavnog sustava',
        'Osoba boluje od bolesti srca i krvnih žila, zloćudnih bolesti, bolesti jetre, AIDS-a, šećerne bolesti (osobe liječene inzulinskom terapijom), živčanih i duševnih bolesti',
        'Osoba je ovisnik o alkoholu ili drogama',
        'Muškarci koji su u životu imali spolne odnose s drugim muškarcima',
        'Osobe koje često mijenjaju seksualne partnere (promiskuitetne osobe)',
        'Žene i muškarci koji su imali spolni odnos s prostitutkama',
        'Osobe koje su uzimale drogu intravenskim putem',
        'Osobe koje su liječene zbog spolno prenosivih bolesti (sifilis, gonoreja)',
        'Osobe koje su HIV-pozitivne',
        'Seksualni partneri gore navedenih osoba'
    ]

    const indexes = [];
    for(let i = 0; i < questionsList.length; i++) {
        indexes.push(i)
    }

    const [donorDataForm, setDonorDataForm] = useState({
        name: "",
        surname: "",
        role: {
            id: 0,
            name: ""
        },
        blood: {
            id: 0,
            name: "",
            upperbound: 0,
            lowerbound: 0,
            supply: 0
        },
        username: "",
        rejected: false,
        birthplace: "",
        address: "",
        workplace: "",
        mobilePrivate: "",
        mobileBusiness: "",
        birthdate: ""
    });

    const [mjestoDarivanja, setMjestoDarivanja] = useState("");
    const [questionsForm, setQuestionsForm] = useState(() => {
        let temp = []; 
        for(let j= 0; j < questionsList.length; j++) {
            temp.push('false')
        }
        return temp;
    });
/*
    React.useEffect( () => {
        console.log(props.donorData);
        setDonorDataForm(props.donorData);
    }, [props.donorData])  //Valentin je rekao da je ovo nepreproruciljivo rjesenje, ali radi
*/

    useEffect(() => {
        setDonorDataForm(props.donorData);
    });

    function onChange(event) {
        setMjestoDarivanja(event.target.value);
    }

    function onChangeQuestionsForm(event) { 
        let newQuestionsForm = [...questionsForm]
        newQuestionsForm[event.target.name] = event.target.value;
        setQuestionsForm(newQuestionsForm);
    }

    async function onSubmit(e) {
        e.preventDefault();

        let upitnikData = {
            1: questionsForm[0],
            2: questionsForm[1],
            3: questionsForm[2],
            4: questionsForm[3],
            5: questionsForm[4],
            6: questionsForm[5],
            7: questionsForm[6],
            8: questionsForm[7],
            9: questionsForm[8],
            10: questionsForm[9],
            11: questionsForm[10],
            12: questionsForm[11],
            13: questionsForm[12],
            14: questionsForm[13],
            15: questionsForm[14],
            16: questionsForm[15],
            17: questionsForm[16],
            18: questionsForm[17],
            19: questionsForm[18],
            20: questionsForm[19]
        };

        const data = {
            id_donora: donorDataForm.username,
            mjesto_darivanja: mjestoDarivanja,
            upitnik: upitnikData
        };

        axios.post(SPRING_URL.concat('/healthDataAnswered'),
            data).then(res => {
                console.log(res);
                if (res.status === 200) {
                    alert(res.data);
                    props.setView("NORMAL");
                }
                if (res.status === 400) {
                    alert(res);
                }
            }).catch(err => {
                alert(err.response.data);
                props.setView("NORMAL");
            });
    }

    function bloodName(name){
        switch(name){
            case "A_PLUS":
                return "A+"
            case "AB_PLUS":
                return "AB+"
            case "B_PLUS":
                return "B+"
            case "ZERO_PLUS":
                return "O+"
            case "A_MINUS":
                return "A-"
            case "AB_MINUS":
                return "AB-"
            case "B_MINUS":
                return "B-"
            case "ZERO_MINUS":
                return "O-"       
            default:
                break;
        }
    }

    return (
        <div>
            <div className="container col-md-6 col-md-offset-4 border border-danger rounded">
                <Form className="mt-3 mb-3" onSubmit={onSubmit}> 
                        {indexes.map(index =>
                            <Row key={index} className="mb-3"> 
                                <Form.Group as={Col} md="6">
                                    <Form.Label>{questionsList[index]}</Form.Label>
                                </Form.Group>
                                <Form.Group as={Col} md="6">
                                        <Form.Select
                                            name={index}
                                            onChange={onChangeQuestionsForm}
                                            value={questionsForm[index]}
                                        >
                                            <option value='true'>Da</option>
                                            <option value='false'>Ne</option>  
                                        </Form.Select>
                                </Form.Group>
                            </Row>
                        )}
                    <Row className="mb-2">
                        <Form.Group as={Col} md="6">
                            <Form.Label>First name</Form.Label>
                            <Form.Control 
                                type="text"
                                name="givenName"
                                value={donorDataForm.name}
                                disabled
                            />
                        </Form.Group>
                        <Form.Group as={Col} md="6">
                            <Form.Label>Last name</Form.Label>
                            <Form.Control 
                                type="text"
                                name="familyName"
                                value={donorDataForm.surname}
                                disabled
                            />
                        </Form.Group>
                    </Row>
                    <Row className="mb-2">
                        <Form.Group as={Col} md="6">
                            <Form.Label>Krvna grupa: </Form.Label>
                            <Form.Control 
                                disabled
                                type="text"
                                name="bloodType"
                                value={bloodName(donorDataForm.blood.name)}
                            />
                        </Form.Group>
                        <Form.Group as={Col} md="6">
                            <Form.Label>Mjesto doniranja: </Form.Label>
                            <Form.Control 
                                required
                                type="text"
                                name="mjestoDarivanja"
                                onChange={onChange}
                                value={mjestoDarivanja}
                            />
                        </Form.Group>
                    </Row>
                    <Row className="mb-3">
                        <Form.Group as={Col} md="6">
                            <Button className="btn-danger mb-1" type="submit">
                                Evidentiraj donaciju
                            </Button>
                        </Form.Group>
                        <Form.Group as={Col} md="4">
                            <Button className="btn-danger" onClick={() => props.setView("NORMAL")}>
                                Vrati se nazad
                            </Button>
                        </Form.Group>
                    </Row>
                </Form>
            </div>
        </div>
    )
}

export default DonationForm

