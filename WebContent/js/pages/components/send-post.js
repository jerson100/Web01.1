
((window, document, undefined) => {
    let inputT = document.getElementById("send-container");
    let postC = document.getElementById("post-container-send");
    let desk = document.getElementById("desktop");
    let previewImg = document.getElementById("img_preview_post");
    let senFileIntput = document.getElementById("send-file-post");
    let addFileInput = document.getElementById("addFile");
    let form = document.getElementById("form-post");
    let btnSend = document.getElementById("send_post");
    let post_articles = document.querySelector(".post-container-profile");

    const init = () => {

        sendFilePost();

        sendFileXAjax();

    };

    const sendFileXAjax = () => {

        if (!form)
            return;

        btnSend.addEventListener('click', function (e) {

            e.preventDefault();

            console.log(form);

            let data = new FormData(form);

            let xhr = new XMLHttpRequest();

            xhr.open("POST", "post", true);
            
            let loader = createLoad();

            xhr.addEventListener('load', response => {

                console.log(response);

                loader.remove();
                
                if (response.target.status === 200) {

                	//removemos el focus
                	
                	if(postC.classList.contains("focus-desktop")){
                		postC.classList.remove("focus-desktop");
                	}
                	
                	let json = JSON.parse(response.target.responseText);
                	
                	printMessage(json);
                	
                	if(json.status){
                		
                		printPost(json);
                		
                	}

                } else {

                    //imprimimos un mensaje modal
                    //con el error para decirle al user
                    //porque no se pudo publicar...
                	let json = JSON.parse(response.target.responseText);
                	
                	printMessage(json);

                }

            });

            xhr.send(data);

        });

    };
    
    const printMessage = (response)=>{
    	
    	let m = new MessageModal({
            "body" : `
                <div>
                    <p>
                        ${response.message}
                    </p>
                </div>
            `,
            "position":{
                "x" : "left",
                "y" : "top",
            },
            "max_width": "380px"
        });
    	
    	m.open();
    	
    };

    const printPost = (responseText) => {

        let article = document.createElement("article");
        article.setAttribute("class", "article-post");
        article.setAttribute("style","animation: maxHeight 1.2s 0s ease-in-out both");
        article.innerHTML = `
                        <div class="user-container">
                            <a class="post_userwrapper-img" style="display: block" href="users/profile?id='${responseText.post.idUser}'">
                                <img class="user-img up_user" src="${responseText.urlImgUser}" alt="${responseText.username}" title="${responseText.username}">
                            </a>
                        </div>
                        <div class="publication-container">
                            <div class="publication-user">
                                <p class="username t3"><a href="users/profile?id=${responseText.post.idUser}">${responseText.username}</a></p>
                                <div class="type-user">
                                    
                                </div>
                                <time>
                                    Hace instantes
                                </time>
                            </div>
                            <div class="publication-title">
                                <h1 class="normal mr-b5">
                                   ${responseText.post.title}
                                </h1>
                                <p class="mr-b5">
                                    ${responseText.post.description}
                                </p>
                            </div>
                            <div class="publication-img">
                                <img src="${responseText.post.urlImage}" alt="${responseText.post.username}">
                            </div>
                            <div class="publication-reactions">
                                <div class="reactions-inner">
                                    <div class="comments">
                                        <img src="imgs/svg/mensaje.svg" alt="">
                                        <span class="count">0</span>
                                    </div>
                                    <div class="love">
                                        <img src="imgs/svg/corazon.svg" alt="">
                                        <span class="count">${responseText.post.countReaction}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                            
                        <div class="pointer-publication" style="display:${responseText.post.isSet == '0' ? 'none' : ''};">
                            <img class="pointer-icon" src="imgs/svg/fijado3.svg" alt="icono de fijaci�n">
                            <span class="small p-fijada">Publicaci�n fijada</span>
                        </div>
                            
                    `;

        /*
         * Insertamos los tipos de usuarios
         */
        
        if(responseText.typeUser){
        	let type_user = article.querySelector(".type-user");
            responseText.typeUser.forEach((type)=>{
            	let img = document.createElement('img');
            	img.src = type.url;
            	img.title = type.name;
            	img.alt = type.name;
            	type_user.appendChild(img);
            });
        }
        
        /*
         
         Tenemos que identificar si no tenemos publicaciones previas
         
         */
        /*
        let img = document.createElement("img");
        img.src = responseText.post.urlImage;
        img.alt = responseText.post.description;
        */
        let firstElement = post_articles.firstElementChild;

        if (firstElement.tagName === 'P') {

            firstElement.remove();

            post_articles.appendChild(article);

        } else {

            post_articles.insertBefore(article, firstElement);

        }
        
        
            
           // console.log("Updated");
            
           // article.querySelector(".publication-img").appendChild(img);
            
       
        
    };

    const sendFilePost = () => {

        if (inputT && postC && desk) {

            addFocusDesktop();

            if (addFileInput && senFileIntput) {
                addFileInput.addEventListener("click", () => {
                    senFileIntput.click();
                });

                senFileIntput.addEventListener("change", updateFile);
            }

        }

    };

    const addFocusDesktop = () => {

        inputT.addEventListener('click', e => {
            postC.classList.add("focus-desktop");
        });

        desk.addEventListener('click', e => {
            if (e.target === desk) {
                postC.classList.remove("focus-desktop");
            }
        });

    };

    function updateFile() {

        let reader = new FileReader();

        reader.addEventListener('load', e => {

            let img = document.createElement("img");
            img.src = e.target.result;//nos devuelve la url con el binario

            previewImg.innerHTML = "";
            img.style.animation = "updateProfile 2s 1 ease";
            previewImg.appendChild(img);

        });

        reader.readAsDataURL(this.files[0]);

    }
    ;

    init();

})(window, document);
       