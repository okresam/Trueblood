import React from 'react';
import {useState} from "react";
import {useHistory} from "react-router-dom";
import "./SignInForm.css";
import axios from 'axios';
import { Form, Button } from 'react-bootstrap';
import { SPRING_URL } from './Constants';

function Confirm(props) {

    const [passwordForm, setPasswordForm] = useState({password: ""});
    const [error, setError] = useState("");
    const [passwordShown, setPasswordShown] = useState(false);
    const history = useHistory();

    let code = (new URLSearchParams(window.location.search)).get('code');

    function onSubmit(e) {
        e.preventDefault();
        setError("");
    
        if(!validatePassword()){
            e.stopPropagation();
        } else {
            axios
                .get(SPRING_URL.concat('/user/add/confirm'),
                    { headers: { code: code, password: passwordForm.password } })
                .then(() => {
                    alert("Lozinka postavljena!");
                    history.push('/login');
                }).catch(() => {
                setError("Login failed!");
            });
        }

       
    }

    function onChange(event) {
        validatePassword();
        const {name, value} = event.target;
        let newForm = {password:passwordForm.password};
        newForm[name] = value;
        setPasswordForm(newForm);
    }

    function validatePassword(){
        var password1 = document.getElementById("password"), password2 = document.getElementById("confirm_password");
        if(password1.value !== password2.value) {
            password2.setCustomValidity("Passwords Don't Match");
            return false;
        } else {
            password2.setCustomValidity('');
            return true;
        }
    }

    function togglePassword(){
        setPasswordShown(!passwordShown);
    };

    return (
        <div className="container col-md-4 col-md-offset-4 border border-danger rounded">
            <Form className="mt-3 mb-3" onSubmit={onSubmit}>
                <Form.Group className="col-xs-2">
                    <Form.Label>Password</Form.Label>
                    <Form.Control 
                        required
                        type={passwordShown ? "text" : "password"}
                        name="password"
                        id="password"
                        value={passwordForm.password}
                        onChange={onChange}  
                    />
                </Form.Group>
                <br/>
                <Form.Group>
                    <Form.Label>Confirm password</Form.Label>
                    <Form.Control 
                        required
                        type={passwordShown ? "text" : "password"}
                        name="confirm_password"
                        id="confirm_password"
                        onKeyUp={validatePassword} 
                    />
                </Form.Group>
                <hr/>
                <Button className="btn-danger" type="submit">
                    Submit
                </Button>
                <Form.Check type="checkbox" label="Show password" onClick={() => togglePassword()}></Form.Check>
                <div>{error}</div>
            </Form>
        </div>
    )

}

export default Confirm