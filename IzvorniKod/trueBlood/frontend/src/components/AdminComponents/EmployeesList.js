import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {Table} from 'react-bootstrap';
import SignInForm from '../SignInForm';
import { SPRING_URL } from '../Constants';
import EmployeeData from '../EmployeeComponents/EmployeeData'

function EmployeesList(props) {
    
    const [employeesList, setEmployeesList] = useState([]);

    async function getEmployeesData() {
        let data = await axios.get(SPRING_URL.concat('/employees')).then(res => res.data);
        setEmployeesList(data);
    }

    useEffect(() => {
        getEmployeesData();
    }, []);

    const NORMAL = "NORMAL", DETAILS = "DETAILS", ADDING = "ADDING"
    const [view, setView] = useState(NORMAL);

    const [editedEmployee, setEditedEmployee] = useState({username: "", name:"", surname:"", role:"", blood:{}, rejected:"", birthplace: "", address: "", workplace: "", mobilePrivate: "", mobileBusiness: "", birthdate: ""});

    const [filter, setFilter] = useState("");

    function filterFunction(event) {
        setFilter(event.target.value);
    }

    function setViewTo(view, username) {
        let index = employeesList.findIndex((element)=>element.username === username)
        switch (view) {
            case DETAILS:
                setEditedEmployee(employeesList[index]);
                break;
            default:
                break;
        }
        if(view === NORMAL){
            getEmployeesData();
        }
        setView(view)
    }

    function deleteEmployee(id){
        console.log("Brisem " + id);
        const data = {
            employeeid: id
        };
        axios.delete(SPRING_URL.concat('/deactivateEmployee'), {data : data}
            ).then(res => {
                console.log(res);
                if (res.status === 200) {
                    alert("Djelatnik izbrisan!");
                    getEmployeesData();
                }
                if (res.status === 400) {
                    alert("Došlo je do greške!");
                }
            }).catch(err => {
                alert("Došlo je do greške!");
            });
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
                                    <button className="btn btn-danger ms-1" onClick={() => setViewTo(ADDING)}>Dodaj djelatnika</button>
                                </div>
                                <div>
                                    <Table hover>
                                        <thead>
                                            <tr>
                                                <th>Ime</th>
                                                <th>Prezime</th>
                                            
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {employeesList.map(employee =>
                                                <tr key={employee.username} hidden={!(employee.name.includes(filter) || employee.surname.includes(filter))}>
                                                    <td>{employee.name}</td>
                                                    <td>{employee.surname}</td>
                                                    
                                                    <td><button className="btn btn-danger" onClick={() => deleteEmployee(employee.username)}>Obriši djelatnika</button></td>
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
                                <EmployeeData mode="ADMIN_ACCESSING_DATA" username={editedEmployee.username} setView={setViewTo}/>
                            </div>
                        )
                    case ADDING:
                        return(
                            <div>  
                                 <SignInForm mode="ADDING_EMPLOYEE" setView={setViewTo}/>
                            </div>
                        )
                    default:
                        break;
                }
            })()}
       </div>
       
    )
}

export default EmployeesList;