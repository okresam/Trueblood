import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {SPRING_URL} from '../Constants';
import AuthHandler from '../AuthHandler';
import {ListGroup, ListGroupItem} from 'react-bootstrap';


function Messages(props) {
    
    const [messages, setMessages] = useState({months: "false", belowLower: "false"});

    async function getMessages(){
        let data = await axios.get(SPRING_URL.concat('/user/getMessages'), {
                                    headers: {
                                        username: AuthHandler.getLoggedInUserName()
                                    }
                                }).then(res => res.data);
        console.log(data);
        setMessages(data);
    }

    useEffect(() => {
        getMessages();
    }, []);

    return(
        <ListGroup>
            <ListGroupItem hidden={!messages.belowLower ? true : false}>Razina vaše krvne vrste je pala ispod donje granice! Pozivamo vas na doniranje!</ListGroupItem>
            <ListGroupItem className="border" hidden={!messages.months ? true : false}>Još niste donirali ili je prošlo tri mjeseca od uspješne donacije. Pozivamo vas na doniranje!</ListGroupItem>
            <ListGroupItem className="border" hidden={messages.belowLower || messages.months ? true : false}>Nemate poruka!</ListGroupItem>
        </ListGroup>    

    )
}

export default Messages;