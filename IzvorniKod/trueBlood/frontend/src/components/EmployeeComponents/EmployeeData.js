import React from 'react';
import { useEffect } from 'react';
import { Form, Row, Col, Button } from 'react-bootstrap';
import axios from "axios"
import AuthHandler from '../AuthHandler';
import {SPRING_URL} from '../Constants';

function EmployeeData(props) {

    //props.mode može biti "EMPLOYEE_ACCESSING_DATA" ili "ADMIN_ACCESSING_DATA" ili "ADMIN_ADDING_EMPLOYEE" //iako to ovdje možda ne treba jer ne postoje podaci djelatnika koja admin može mijenjati, a djelatnik ne
    //props.username
    const [employeeDataForm, setEmployeeDataForm] = React.useState({givenName:"", familyName:"", OIB:"", dateOfBirth:"", birthPlace:"",  residenceAdress:"", privatePhoneNumber:"", workPhoneNumber:"", email:""});
    const [oldEmployeeDataForm, setOldEmployeeDataForm] = React.useState(); //za cuvanje stare forme, iz nekog razloga ne radi kada samo napisem let oldMydataForm;
    const [editingMode, setEditingMode]= React.useState(false)

    let targetUsername;

    switch(props.mode){
        case "EMPLOYEE_ACCESSING_DATA":
            targetUsername = AuthHandler.getLoggedInUserName();
            break;
        case "ADMIN_ACCESSING_DATA":
            targetUsername = props.username;
            break;
        default:
            break;
    }

    async function getEmployeeData(){
        let data = await axios.get(SPRING_URL.concat('/user/getUserInfo'), {
            headers: {
                'username': targetUsername
            }
        }).then(res => res.data);
        var time = Date.parse(data.birthdate);
        console.log(time);
        var date = new Date(time);
        var dateFormat = date.getFullYear() + '-'
            + ('0' + (date.getMonth() + 1)).slice(-2) + '-'
            + ('0' + date.getDate()).slice(-2);
        let newForm = {
            givenName: data.name, familyName: data.surname,
            OIB: data.oib, dateOfBirth: dateFormat, birthPlace: data.birthplace,
            residenceAdress: data.address, workplaceName: data.workplace,
            privatePhoneNumber: data.mobilePrivate, workPhoneNumber: data.mobileBusiness, email: data.email, bloodType: bloodName(data.bloodTypeName), isRejected: data.rejected, gender: data.genderMale,
        };
        setEmployeeDataForm(newForm);
    }

    useEffect( () => {
        getEmployeeData();    
    }, []);

    function onChange(event) {

        if (editingMode) {
            const {name, value} = event.target;
            let newForm = { ...employeeDataForm };
            newForm[name] = value;
            setEmployeeDataForm(newForm);
        }

    }

    function onSubmit(e) {
        e.preventDefault();

        const data = {
            name: employeeDataForm.givenName,
            surname: employeeDataForm.familyName,
            genderMale: employeeDataForm.gender,
            birthplace: employeeDataForm.birthPlace,
            address: employeeDataForm.residenceAdress,
            workplace: employeeDataForm.workplaceName,
            mobilePrivate: employeeDataForm.privatePhoneNumber,
            mobileBusiness: employeeDataForm.workPhoneNumber,
            birthdate: employeeDataForm.dateOfBirth,
            rejected: employeeDataForm.isRejected,
        };

        const headers = {
            'username': targetUsername
        };
        return axios.post(SPRING_URL.concat('/user/editUserInfo'),
            data, {
            headers: headers
        }).then(res => {
                console.log(res);
                if (res.status === 200) {
                    getEmployeeData();
                    setOldEmployeeDataForm({ ...employeeDataForm });
                    setEditingMode(false);
                    alert("Promijene spremljene!");
                }
                if (res.status === 400) {
                    alert("Došlo je do greške!");
                }
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
                return "AB+"
            case "B_MINUS":
                return "B+"
            case "ZERO_MINUS":
                return "O+"       
            default:
                break;
        }
    }

    function enterEditingMode() {
        setEditingMode(true)
        setOldEmployeeDataForm({ ...employeeDataForm})  //ovako se kopira objekt

    }

    function returnToOld() {
        setEditingMode(false)
        setEmployeeDataForm(oldEmployeeDataForm);
    }
    
    return (
        <div className="container col-md-6 col-md-offset-2 border border-danger rounded">
            <Form className="mt-3 mb-3" onSubmit={onSubmit}>
                <Row className="mb-2">
                    <Form.Group as={Col} md="6">
                        <Form.Label>Ime</Form.Label>
                        <Form.Control 
                            required
                            type="text"
                            name="givenName"
                            value={employeeDataForm.givenName}
                            onChange={onChange}
                            placeholder="Ime"  
                        />
                    </Form.Group>
                    <Form.Group as={Col} md="6">
                        <Form.Label>Prezime</Form.Label>
                        <Form.Control 
                            required
                            type="text"
                            name="familyName"
                            value={employeeDataForm.familyName}
                            onChange={onChange}
                            placeholder="Prezime"  
                        />
                    </Form.Group>
                </Row>
                <Row className="mb-3">
                    <Form.Group as={Col} md="6">
                        <Form.Label>OIB</Form.Label>
                        <Form.Control 
                            readOnly={props.mode === "EMPLOYEE_ACCESSING_DATA"}
                            type="text"
                            name="OIB"
                            disabled={props.mode === "EMPLOYEE_ACCESSING_DATA"}
                            value={employeeDataForm.OIB}
                            onChange={onChange}
                            minLength="11"
                            maxLength="11"
                            placeholder="OIB"  
                        />
                    </Form.Group>
                    <Form.Group as={Col} md="6">
                        <Form.Label>Datum rođenja</Form.Label>
                        <Form.Control 
                            required
                            type="date"
                            name="dateOfBirth"
                            value={employeeDataForm.dateOfBirth}
                            onChange={onChange}  
                        />
                    </Form.Group>
                </Row>
                <Row className="mb-3">
                    <Form.Group as={Col} md="6">
                        <Form.Label>Mjesto rođenja</Form.Label>
                        <Form.Control 
                            required
                            type="text"
                            name="birthPlace"
                            value={employeeDataForm.birthPlace}
                            onChange={onChange}  
                            placeholder="Mjesto rođenja"
                        />
                    </Form.Group>
                    <Form.Group as={Col} md="6">
                        <Form.Label>Adresa prebivališta</Form.Label>
                        <Form.Control 
                            required
                            type="text"
                            name="residenceAdress"
                            value={employeeDataForm.residenceAdress}
                            onChange={onChange} 
                            placeholder="Adresa prebivališta"
                        />
                    </Form.Group>
                </Row>
                
                <Row className="mb-3">
                    <Form.Group as={Col} md="6">
                        <Form.Label>Privatni telefon</Form.Label>
                        <Form.Control 
                            required
                            type="tel"
                            pattern="[0-9]{10}"
                            name="privatePhoneNumber"
                            value={employeeDataForm.privatePhoneNumber}
                            onChange={onChange}  
                            placeholder="0123456789"
                        />
                    </Form.Group>
                    <Form.Group as={Col} md="6">
                        <Form.Label>Poslovni telefon</Form.Label>
                        <Form.Control 
                            type="tel"
                            pattern="[0-9]{10}"
                            name="workPhoneNumber"
                            value={employeeDataForm.workPhoneNumber}
                            onChange={onChange} 
                            placeholder="0123456789"
                        />
                    </Form.Group>
                </Row>
                <Row className="mb-3">
                    <Form.Group as={Col} md="12">
                        <Form.Label>E-mail</Form.Label>
                        <Form.Control 
                            readOnly={props.mode === "EMPLOYEE_ACCESSING_DATA"}
                            type="email"
                            name="email"
                            value={employeeDataForm.email}
                            onChange={onChange}  
                            placeholder="E-mail"
                        />
                    </Form.Group>
                </Row>
                <Row className="mb-2">
                    <Form.Group as={Col} md="4" className="me-5 mb-1">
                        <Button hidden={!editingMode} className="btn-danger" type="submit">
                            Spremi promjene
                        </Button>
                        <Button hidden={editingMode} className="btn-danger" onClick={enterEditingMode}>
                            Promijeni osobne podatke
                        </Button>
                    </Form.Group>
                    <Form.Group as={Col} md="3" className="mb-1">
                        <Button hidden={!editingMode} className="btn-danger" onClick={returnToOld}>
                            Poništi izmjene
                        </Button>
                    </Form.Group>
                    {(() => {
                        if(props.mode === "ADMIN_ACCESSING_DATA"){
                            return (
                                <Form.Group as={Col} md="3">
                                    <Button className="btn-danger" onClick={() => props.setView("NORMAL")}>
                                        Vrati se nazad
                                    </Button>
                                </Form.Group>
                            );
                        }
                    })()}
                </Row>
            </Form>
        </div>
    )
    
}

export default EmployeeData;