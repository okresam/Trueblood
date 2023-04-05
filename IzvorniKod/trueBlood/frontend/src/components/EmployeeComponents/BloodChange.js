import React, { useState, useEffect } from 'react';
import axios from "axios"
import { Form, Row, Col, Button} from 'react-bootstrap';
import AuthHandler from '../AuthHandler';
import {SPRING_URL} from '../Constants';

function BloodChange(props) {

    const [bloodData, setBloodData] = useState([]);
    const [error, setError] = useState("");
    const [bloodChange, setBloodChange] = useState({});
    const [bloodConsumption, setBloodConsumption] = useState({bloodType:"", quantity:0, location:"", employee: AuthHandler.getLoggedInUserName()});

    async function getBloodData() {
        let data = await axios.get(SPRING_URL.concat('/bloodGroups')).then(res => res.data);
        setBloodData(data);
        setBloodChange(data[0]);
        setBloodConsumption(values => ({
            ...values, 
            bloodType: bloodName(data[0].name)
        }));
    }

    useEffect(() => {
        getBloodData();
    }, []);

    function onSubmit(e) {
        e.preventDefault();
        setError("");
        if(bloodConsumption.quantity < 1){
            setError("Količina krvi mora biti barem jedan!");
            return;
        }
        if(subtract(bloodChange.supply, bloodConsumption.quantity) < 0){
            setError("Nema toliko krvi za poslati!");
            return;
        }
        var d = new Date();
        Number.prototype.padLeft = function(base,chr){
            var  len = (String(base || 10).length - String(this).length)+1;
            return len > 0? new Array(len).join(chr || '0')+this : this;
        }
        var timestamp = [d.getFullYear(),
                (d.getMonth()+1).padLeft(),
               d.getDate().padLeft()].join('-') +' ' +
              [d.getHours().padLeft(),
               d.getMinutes().padLeft(),
               d.getSeconds().padLeft()].join(':');

        var bloodConsumptionData = bloodConsumption;
        bloodConsumptionData.timestamp = timestamp;

        console.log(bloodConsumptionData);
        
        return axios.post(SPRING_URL.concat('/recordChange'), bloodConsumption)
                .then(res => {
                    console.log(res);
                    getBloodData();
                    alert("Krv poslana!");
                    setBloodConsumption(values => ({
                        ...values, 
                        quantity: 0
                    }));
                })
                .catch(err => {
                    console.log(err);
                });
                
    }

    function onChange(event) { 
        const { name, value } = event.target; 
        if(name === "bloodTypeName"){
            var result = bloodData.find(blood => {
                return bloodName(blood.name) === value; 
            })
            console.log(result);
            setError("");
            setBloodConsumption(values => ({
                ...values, 
                bloodType: bloodName(result.name),
                quantity: 0
            }));
            setBloodChange(result);
        } else {
            setBloodConsumption(values => ({
                ...values, 
                [name]: value
            }));
        }
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
                return "0+"
            case "A_MINUS":
                return "A-"
            case "AB_MINUS":
                return "AB-"
            case "B_MINUS":
                return "B-"
            case "ZERO_MINUS":
                return "0-"       
            default:
                break;
        }
    }

    function subtract(one, two){
        return Number(one) - Number(two);
    }

    return(
        <div className="container">
            <div className="row">
                <div className="container col-md-3 border border-danger rounded">
                    <Form className="mt-3 mb-3" onSubmit={onSubmit}>
                        <Row className="mb-2">
                            <Form.Group as={Col} md="6">
                                <Form.Label>Vrsta krvi</Form.Label>
                                <Form.Select
                                    name="bloodTypeName"
                                    onChange={onChange}
                                    value={bloodName(bloodChange.name)}
                                    required
                                >
                                    <option value='A+'>A+</option>
                                    <option value='A-'>A-</option>
                                    <option value='B+'>B+</option>
                                    <option value='B-'>B-</option>
                                    <option value='0+'>0+</option>
                                    <option value='0-'>0-</option>
                                    <option value='AB+'>AB+</option>
                                    <option value='AB-'>AB-</option>
                                </Form.Select>
                            </Form.Group>
                        </Row>
                        <Row className="mb-2">
                            <Form.Group as={Col} md="6">
                                <Form.Label>Količina</Form.Label>
                                <Form.Control
                                    required
                                    type="text"
                                    name="quantity"
                                    value={bloodConsumption.quantity}
                                    onChange={onChange}
                                    placeholder="Količina"
                                />
                            </Form.Group>
                            <Form.Group as={Col} md="6">
                                <Form.Label>Institucija</Form.Label>
                                <Form.Control
                                    required
                                    type="text"
                                    name="location"
                                    value={bloodConsumption.location}
                                    onChange={onChange}
                                    placeholder="Institucija"
                                />
                            </Form.Group>
                        </Row>  
                        <Row className="mb-3">
                            <Form.Group as={Col} md="6">
                                <Button className="btn-danger" type="submit">
                                    Smanji zalihu
                                </Button>
                                <div>{error}</div>
                            </Form.Group>
                        </Row>
                    </Form>
                </div>
                <div className="col">
                    <p>Krv: {bloodName(bloodChange.name)}</p>
                    <p>Gornja granica: {bloodChange.upperbound}</p>
                    <p>Donja granica: {bloodChange.lowerbound}</p>
                    <p>Trenutna zaliha: {bloodChange.supply}</p>
                    <p>Nakon slanja: {subtract(bloodChange.supply, bloodConsumption.quantity)}</p>
                </div>
            </div>
        </div>
    )
}

export default BloodChange;