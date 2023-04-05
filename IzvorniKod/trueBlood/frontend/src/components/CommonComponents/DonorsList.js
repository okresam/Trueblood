import React, { useState, useEffect } from 'react';
import axios from "axios"
import DonorData from "./DonorData";
import DonationForm from "../EmployeeComponents/DonationForm";
import {Table} from 'react-bootstrap';
import SignInForm from '../SignInForm';
import { SPRING_URL } from '../Constants';

function DonorsList(props) {

    const [donorsList, setDonorsList] = useState([]);

    async function getDonorsData() {
        let data = await axios.get(SPRING_URL.concat('/donorList')).then(res => res.data);
        console.log(data);
        setDonorsList(data);
    }

    useEffect(() => {
        getDonorsData();
    }, []);

    const NORMAL = "NORMAL", DETAILS = "DETAILS", ADDING = "ADDING", MAKING_DONATION = "MAKING_DONATION"
    const [view, setView] = useState(NORMAL);

    const [editedDonor, setEditedDonor] = useState({username: "", name:"", surname:"", role:"", blood:{}, rejected:"", birthplace: "", address: "", workplace: "", mobilePrivate: "", mobileBusiness: "", birthdate: ""});

    const [donorMakingDonation, setDonorMakingDonation] = useState({username: "", name:"", surname:"", role:"", blood:{}, rejected:"", birthplace: "", address: "", workplace: "", mobilePrivate: "", mobileBusiness: "", birthdate: ""});
  
    const [filter, setFilter] = useState("");

    function filterFunction(event) {
        setFilter(event.target.value);
    }

    function setViewTo(view, username) {
        let index = donorsList.findIndex((element)=>element.username === username)
        switch (view) {
            case DETAILS:
                setEditedDonor(donorsList[index]);
                break;
            case MAKING_DONATION:
                setDonorMakingDonation(donorsList[index]);
                break;
            default:
                break;
        }
        if(view === NORMAL){
            getDonorsData();
        }
        setView(view)
    }

    function deleteDonor(id){
        console.log("Brisem " + id);
        const data = {
            donorid: id
        };
        axios.delete(SPRING_URL.concat('/deactivateDonor'), {data : data}
            ).then(res => {
                console.log(res);
                if (res.status === 200) {
                    alert("Donor izbrisan!");
                    getDonorsData();
                }
                if (res.status === 400) {
                    console.log("Error!");
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
                return "AB-"
            case "B_MINUS":
                return "B-"
            case "ZERO_MINUS":
                return "O-"       
            default:
                break;
        }
    }

    return(
        <div>
            {(() => {
                console.log(view);
                switch(view){
                    case NORMAL:
                        return(
                            <div className="container">
                                <div>
                                    <input type="text" name="filter" onChange={filterFunction} placeholder="Pretraži..."/>
                                    <button hidden={props.mode === "ADMIN"} className="btn btn-danger ms-1" onClick={() => setViewTo(ADDING)}>Dodaj donora</button>
                                </div>
                                <div>
                                    <Table hover>
                                        <thead>
                                            <tr>
                                                <th>Ime</th>
                                                <th>Prezime</th>
                                                <th>Krvna grupa</th>
                                                <th>Trajno odbijen</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {donorsList.map(donor =>
                                                <tr key={donor.username} hidden={!(donor.name.includes(filter) || donor.surname.includes(filter) || bloodName(donor.blood.name).includes(filter))}>
                                                    <td>{donor.name}</td>
                                                    <td>{donor.surname}</td>
                                                    <td>{bloodName(donor.blood.name)}</td>
                                                    <td>{!donor.rejected ? "Ne" : "Da"}</td>
                                                    <td><button className="btn btn-outline-danger" hidden={props.mode === "ADMIN"} onClick={() => setViewTo(DETAILS, donor.username)}>Detalji</button></td>
                                                    <td><button className="btn btn-outline-danger" hidden={donor.rejected === true || props.mode === "ADMIN"} onClick={() => setViewTo(MAKING_DONATION, donor.username)}>Obavi donaciju</button></td>
                                                    <td><button className="btn btn-danger" onClick={() => deleteDonor(donor.username)} hidden={props.mode!=="ADMIN"}>Obriši donora</button></td>
                                                </tr>
                                            )}
                                        </tbody>
                                    </Table>
                                </div>
                            </div>
                        )
                    case DETAILS:
                        return(
                            <div>
                                <DonorData mode="EMPLOYEE_ACCESSING_DATA" username={editedDonor.username} setView={setViewTo}/>
                            </div>
                        )
                    case ADDING:
                        return(
                            <div>  
                                 <SignInForm mode="ADDING_DONOR" setView={setViewTo}/>
                            </div>
                        )
                    case MAKING_DONATION:
                        return(
                            <div>  
                                <DonationForm donorData={donorMakingDonation} setView={setViewTo}/>
                            </div>
                        )
                    default:
                        break;
                }
            })()}
       </div>
       
    )
}

export default DonorsList;