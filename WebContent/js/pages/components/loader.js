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