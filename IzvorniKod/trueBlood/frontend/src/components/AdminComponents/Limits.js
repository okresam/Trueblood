import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Form, Row, Col, Button } from 'react-bootstrap';
import { SPRING_URL } from '../Constants';

function Limits(props) {

    const [bloodData, setBloodData] = useState([]);
    const [error, setError] = useState("");
    const [bloodBounds, setBloodBounds] = useState({name: "", orgUpper: 0, orgLower: 0, upperbound:0, lowerbound:0, supply: 0});

    async function getBloodData() {
        let data = await axios.get(SPRING_URL.concat('/bloodGroups')).then(res => res.data);
        setBloodData(data);
        setBloodBounds({
            name: data[0].name,
            orgUpper: data[0].upperbound,
            orgLower: data[0].lowerbound,
            upperbound: "",
            lowerbound: "",
            supply: data[0].supply
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

    useEffect(() => {
        getBloodData();
    }, []);

    function onChange(event){
        const { name, value } = event.target; 
        if(name === "name"){
            var result = bloodData.find(blood => {
                return bloodName(blood.name) === value; 
            })
            console.log(result);
            setError("");
            setBloodBounds({
                name: result.name,
                upperbound: "",
                lowerbound: "",
                orgUpper: result.upperbound,
                orgLower: result.lowerbound,
                supply: result.supply
            });
        } else {
            setBloodBounds(values => ({
                ...values, 
                [name]: value
            }));
        }
    }

    function onSubmit(e){
        e.preventDefault();
        setError("");
        if(bloodBounds.upperbound === "" && bloodBounds.lowerbound === ""){
            setError("Nema promjene!");
            return;
        }
        let newBloodBounds = {
            name: bloodName(bloodBounds.name),
            upperbound: bloodBounds.upperbound,
            lowerbound: bloodBounds.lowerbound
        };
        if(newBloodBounds.upperbound === ""){
            if(newBloodBounds.lowerbound > bloodBounds.orgUpper){
                setError("Donja granica ne može biti veća od postojeće gornje!");
                return;
            }
            newBloodBounds.upperbound = bloodBounds.orgUpper;
        }
        if(newBloodBounds.lowerbound === ""){
            if(newBloodBounds.upperbound < bloodBounds.orgLower){
                setError("Gornja granica ne može biti manja od postojeće donje!");
                return;
            }
            newBloodBounds.lowerbound = bloodBounds.orgLower;
        }
        if(newBloodBounds.upperbound < 0 || newBloodBounds.lowerbound < 0){
            setError("Granice moraju biti pozitivne!");
            return;
        }
        console.log(newBloodBounds);
        return axios.post(SPRING_URL.concat('/bloodGroups'), newBloodBounds)
                .then(res => {
                    console.log(res);
                    alert("Granice promijenjene!");
                    getBloodData();
                })
                .catch(err => {
                    console.log(err);
                });
    }

    return(
        <div id="parent" className="container col-md-4 border border-danger rounded">
            <Form className="mt-3 mb-3" onSubmit={onSubmit}>
                <Row>
                    <Form.Group as={Col} md="6">
                        <Form.Label>Vrsta krvi</Form.Label>
                        <Form.Select
                            name="name" 
                            onChange={onChange}
                            value={bloodName(bloodBounds.name)}
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
                    <div className="col">
                        <p>Trenutna zaliha: {bloodBounds.supply} </p>
                        <p>Gornja: {bloodBounds.orgUpper} </p>
                        <p>Donja: {bloodBounds.orgLower} </p>
                    </div>
                </Row>
                <Row>
                    <Form.Label>Nove granice:</Form.Label>
                    <Form.Group as={Col} md="6" className="mb-1">
                        <Form.Control
                            type="number"
                            min={bloodBounds.lowerbound}
                            name="upperbound"
                            value={bloodBounds.upperbound}
                            onChange={onChange}
                            placeholder="Gornja"
                        />
                    </Form.Group>
                    <Form.Group as={Col} md="6">
                        <Form.Control
                            type="number"
                            max={bloodBounds.upperbound}
                            name="lowerbound"
                            value={bloodBounds.lowerbound}
                            onChange={onChange}
                            placeholder="Donja"
                        />
                    </Form.Group>
                </Row>
                <Row className="mb-3 mt-3">
                    <Form.Group as={Col} md="6">
                        <Button className="btn-danger" type="submit">
                            Promijeni granice
                        </Button>
                        <div>{error}</div>
                    </Form.Group>
                </Row>
            </Form>
        </div> 
    )
}

export default Limits;