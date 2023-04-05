import { useEffect, useState } from 'react';
import axios from "axios"
import "../Home";
import 'bootstrap/dist/css/bootstrap.min.css';
import { Accordion } from 'react-bootstrap';
import {SPRING_URL} from '../Constants';

function BloodData(props){

    const [bloodData, setBloodData] = useState([]);

    useEffect(() => {
        async function getBloodData() {
            let data = await axios.get(SPRING_URL.concat('/bloodGroups')).then(res => res.data);
            setBloodData(data);
        }

        getBloodData();
    }, []);

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

    let maximumKrvi = 1500;
    function bloodHeight(amount, upper, lower){
        console.log(amount + " " + upper + " " + lower);
        if(amount > maximumKrvi){
            return "300px";
        } else {

            return amount/maximumKrvi*300+"px";
        }
    }
    function izracunajgranicu(granica){
        if(granica > maximumKrvi){
            return "300px";
        }else{
            return granica/maximumKrvi*300+"px";
        }

    }

    return(
        <div className="container">
            <div className="row justify-content-center">
                <div id="sveSkup" className="col-6 ps-3 border border-danger border-end-0 rounded-start">
                    {bloodData.map(blood => {
                        return (
                            <div className="cijela" key={blood.id}>
                            <div className="imeGrupe">{bloodName(blood.name)}</div>
                            <div className="gornjaGranica" style={{ bottom: izracunajgranicu(blood.upperbound)}}></div>
                            <div className="donjaGranica" style={{ bottom: izracunajgranicu(blood.lowerbound)}}></div>
                            <div className="epruveta"></div>
                            <div className="fix"></div>
                            <div className="kolicKrvi" style={{ height: bloodHeight(blood.supply, blood.upperbound, blood.lowerbound) }}></div>
                        </div>
                        )
                    })}
                </div>
                <Accordion className="col-2 border border-danger border-start-0 rounded-end pe-0">
                        {bloodData.map(blood => {
                            return (
                            <Accordion.Item key={blood.id} eventKey={blood.id}>
                                <Accordion.Header>
                                    {bloodName(blood.name)}
                                </Accordion.Header>
                                <Accordion.Body>
                                    <p>Vrsta: {bloodName(blood.name)}</p>
                                    <p>Zaliha: {blood.supply}</p>
                                    <p>Gornja granica: {blood.upperbound}</p>
                                    <p>Donja granica: {blood.lowerbound}</p>
                                </Accordion.Body>
                            </Accordion.Item>  
                            )
                        })}
                </Accordion>
            </div>
        </div>
    )

}

export default BloodData;