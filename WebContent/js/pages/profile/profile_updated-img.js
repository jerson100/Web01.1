/* global printModal */

'use-strict';

((window, document, undefinied) => {

    document.addEventListener("DOMContentLoaded", () => {

        let img_element = document.getElementById("updated-img");
        let idUser = document.getElementById("profile_idUser").textContent;
        let img_preview;
        
        if(!img_element || !idUser) return;

        img_element.addEventListener('click', e => {

            e.preventDefault();

            let modal = new BigModal({
                "body": `
                    <div>
                        <form action="users/profile" id="form-updated-img" enctype="multipart/form-data">
                            <input type="hidden" name="id" value="${idUser}">
                            <input type="hidden" name="action" value="updatedProfile">
                            <div class="form-item"> 
                                <label for="p_img">Imagen: </label>
                                <input type="file" id="p_img" name="img" >
                            </div>
                            <div class="form-item">
                               <div class="img_preview" id="img_preview">
                               </div>
                            </div>
                            <div class="form-item">
                              <input class="je-btn" type="submit" value="Actualizar Foto">
                            </div>
                       </form>
                    </div>
                `,
                "max_width":"800px"
            });

            modal.open();
            
            img_preview = document.getElementById("img_preview");

            let updatedForm = document.getElementById("form-updated-img");
            
            if(!updatedForm) return;
            
            let fileInput = updatedForm.querySelector("#p_img");
            
            if(!fileInput) return;
            
            fileInput.addEventListener('change',previewFileImg);
           
            addListenerSubmitForm(updatedForm,modal);
            
        });
        
        function previewFileImg(){
          
            //Creamos una instancia de FileReader,
            //el cuál nos va a permitir leer ficheros almacenados en el
            //cliente, de manera asíncrona..
            
            let reader = new FileReader();
            
            img_preview.style.animation = "";
                
            //Cuando termina de leer, los bytes
            
            reader.addEventListener('load',e2=>{
                
                let img = document.createElement("img");
                img.src = e2.target.result;//nos devuelve la url con el binario
                
                console.log(e2);
                
                img_preview.innerHTML = "";
                img.style.animation = "updateProfile 2s 1 ease";
                img_preview.appendChild(img);
                
            });
                
            //Comienza a leer el fichero
            reader.readAsDataURL(this.files[0]);
            
        };
        
        const addListenerSubmitForm = (form,modal)=>{
            
            if(!form)return;
            
            form.addEventListener('submit', function (e) {

                console.log(e.target);

                form[3].disabled = true;

                e.preventDefault();

                //petición ajax
                let data = new FormData(this);

                let xhr = new XMLHttpRequest();

                xhr.open('POST', 'users/profile', true);

                let load = createLoad();

                /*xhr.upload.addEventListener('progress', event => {
                    var complete = (event.loaded / event.total * 100 | 0);
                    console.log(complete);
                });*/

                xhr.addEventListener('load', event => {

                    document.body.removeChild(load);

                    if(event.target.status === 200){
                        
                        let datResponse = JSON.parse(event.target.responseText); 
                        
                        if(datResponse.status)modal.closeAnimation();
                            
                        printModal(datResponse.message,modal.time_close)
                                .then(()=>{
                                    
                                    //actualizamos el perfil
                                    
                                    updateImgs(datResponse.newImage);
                                    
                                    //location.href = `http://localhost:21682/ProyectoWeb01.1/users/profile?id=${datResponse.id}`; 
                                    
                                })
                                .catch(exception=>{
                            
                                    console.log(exception); 

                                    form[3].disabled = false;    
                                    
                                });
                        
                    }else if(event.target.status === 401){
                        
                        /*
                         * Más adelante, tenemos que redireccionar al login 
                         * desde el servidor...
                         */
                        
                        printModal("No autorizado, inicie sesión porfa")
                                .then(()=>{})
                        
                    }
                    
                });

                xhr.send(data);

            });
        };
        
        const updateImgs = (urlImage)=>{
            
            if(!urlImage) return;
            
            let items = Array.from(document.querySelectorAll(".up_user"));
            
            items.forEach(e=>{
                
                updateImg(e,urlImage)
                
            });
            
        };
        
        const updateImg = (element,urlImage)=>{
            
            let parent = element.parentElement;
            let className = element.className;
            let alt = element.alt;
            
            element.remove();
            
            let img = document.createElement("img");
            img.setAttribute("class",className);
            img.src = urlImage;
            img.alt = alt;

            parent.appendChild(img);
            
        };
        
        const printModal = (data, time)=>{
            
            return new Promise((resolve,reject)=>{
                
                if(!data){
                    
                    reject("La información no es válida");
                    
                }else{
                    
                    let previousModal = document.body.querySelector(".je-modal-container_message");

                    if(previousModal)document.body.removeChild(previousModal);

                    let m = new MessageModal({
                        "body" : `
                            <div>
                                <p>
                                    ${data}
                                </p>
                            </div>
                        `,
                        "position":{
                            "x" : "right",
                            "y" : "bottom",
                        },
                        "max_width": "380px"
                    });

                    m.open();
                    
                    if(time){
                        
                        setTimeout(()=>{

                            resolve();

                        },time);
                        
                    }
                    
                }
            
            });
            
        };

        const createLoad = () => {
            let load = document.createElement("div");
            load.classList.add("je-load-container");
            load.innerHTML = `
                    <span></span>
                    <span></span>
                    <span></span>
            `;
            document.body.appendChild(load);
            return load;
        }

    });

})(window, document);
