/* global addListenerRegister, addListener, validateUser, addListenerLogin */

'use-strict';
((window, document, undefined) => {

    window.document.addEventListener("DOMContentLoaded", e => {

        //registrar
        class User{

            constructor(nombre, apellidos, username, email, password, tipoUsuario = "US"){
                this.nombre = nombre;
                this.apellidos = apellidos;
                this.email = email;
                this.username = username;
                this.password = password;
                this.tipoUsuario = tipoUsuario;
            }

        }

        const register_form = document.getElementById("register-form");
        
        const login_form = document.getElementById("login-form");
        
        let ajax, url, data;
        
        const addListener = () => {

            addListenerRegister();
            
            addListenerLogin();
            
        };
        
        const addListenerLogin = () => {

            login_form.addEventListener('submit', function(event){

                event.preventDefault();
                
                ajax = new XMLHttpRequest();
                
                url = "login";
                
                data = new FormData(this);
                
                ajax.open("POST", url, true);
                
                let load = createLoad();

                ajax.addEventListener('load', response => {

                    //eliminamos el load
                        document.body.removeChild(load);
                        
                        event.target[3].disabled = false;

                        switch (response.target.status){

                            case 200:

                                printModal(JSON.parse(response.target.responseText),
                                    Array.from(login_form.querySelectorAll("input[type='text'] , input[type='password']")));
                                
                                break;
                            
                        }

                });
                
                ajax.send(data);

            });
            
        };
        
        const addListenerRegister = () => {

            let user;
            
            register_form.addEventListener('submit', function(event){

                console.log("click");
                
                event.preventDefault();

                user = new User(
                            event.target[1].value,
                            event.target[2].value,
                            event.target[3].value,
                            event.target[4].value,
                            event.target[5].value)

                let val = validateUser(user);
                
                if (val){//no v�lido, mostramos un mensaje

                    console.log(val);
                    
                } else{// v�lido, enviamos los datos al sevidor

                    event.target[6].disabled = true;

                    ajax = new XMLHttpRequest();
                    
                    url = "registro";
                    
                    data = new FormData(this);
                    
                    ajax.open('Post', url, true);
                    
                    let load = createLoad();
                    
                    ajax.addEventListener('load', response => {

                        //eliminamos el load
                        document.body.removeChild(load);
                        
                        event.target[6].disabled = false;

                        switch (response.target.status){

                            case 200:

                                printModal(JSON.parse(response.target.responseText),
                                    Array.from(register_form.querySelectorAll("input[type='text'] , input[type='password'], input[type='email']")));
                                
                                break;
                            
                        }

                    });
                        ajax.send(data);
                }

                });
        }

        const clearRegisterInputs = (inputs)=>inputs.forEach(item=>clearInput(item));
        
        const clearInput = (input) => {
            input.value = "";
            input.nextElementSibling
                 .nextElementSibling
                 .classList
                    .remove("form-item-focus");
        };

        const printModal = (data,inputs) => {
            
            console.log(data);
            
            let previousModal = document.body.querySelector(".je-modal-container_message");

            console.log(previousModal);

            if(previousModal){
                
                document.body.removeChild(previousModal);
                
            }
                
            let m = new MessageModal({
                "body" : `
                    <div>
                        <p>
                            ${data.mensaje}
                        </p>
                    </div>
                `,
                "position":{
                    "x" : "left",
                    "y" : "top",
                },
                "max_width": "380px"
            });

            if(data){

                if(data.estado){

                    document.querySelector(".login-menu img").click(); 
                    
                    clearRegisterInputs(inputs);

                }

            }

            m.open();
            
        }

        const validateUser = (user) => {
            if (!user){
                return "";
            }if (!user.nombre){
                return "El nombre no puede estar vac�o";
            } else if (!user.apellidos){
                return "El apellido no puede estar vac�o";
            } else if (!user.email){
                return "El correo no puede estar vac�o";
            } else if (!user.password){
                return "La contrase�a no puede estar vac�a";
            }
            return "";
        };
        
        addListener();
        
    });

})(window, document);