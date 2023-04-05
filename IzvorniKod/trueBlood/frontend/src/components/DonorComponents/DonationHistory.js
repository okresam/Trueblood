import React, { useState, useEffect} from 'react';
import AuthHandler from '../AuthHandler';
import axios from "axios";
import { Table } from 'react-bootstrap';
import {SPRING_URL} from '../Constants';

function DonationHistory(props) {

    const [donationData, setDonationData] = useState();

    useEffect(() => {
        async function getDonationData() {
            let data = await axios.get(SPRING_URL.concat('/donations'), { headers: { username: AuthHandler.getLoggedInUserName()}})
                .then(res => res.data)
                .catch(err => console.log(err));
            setDonationData(data);
        }
        getDonationData();
    }, []);

    function generatePDF(id){
        const header = {
            id: id
        };
        axios.post(SPRING_URL.concat('/generatePDF'),{}, {headers: header})
                .then(res => alert(res.data))
                .catch(err => console.log(err));
    }

    function formatTime(timeToFormat){
        var time = Date.parse(timeToFormat);
        console.log(time);
        var date = new Date(time);
        var dateFormat = date.getFullYear() + '-'
            + ('0' + (date.getMonth() + 1)).slice(-2) + '-'
            + ('0' + date.getDate()).slice(-2);
        return dateFormat;
    }

    return(
        <div>
            <Table>
                <thead>
                    <tr>
                        <th>Lokacija</th>
                        <th>Datum</th>
                        <th>Uspješnost</th>
                    </tr>
                </thead>
                <tbody>
                    {donationData ? donationData.map((donacija) => {
                        return(
                            <tr key={donacija.id}>
                                <td>{donacija.location}</td>
                                <td>{formatTime(donacija.donationDate)}</td>
                                <td>{donacija.success ? "Uspješna" : "Neuspješna"}</td>
                                <td><button className="btn btn-danger" onClick={()=>generatePDF(donacija.id)}>Generiraj PDF</button></td>
                            </tr>
                        ); 
                    }) : <tr></tr>}
                </tbody>            
            </Table>
        </div> 
    )
}

export default DonationHistory;