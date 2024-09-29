let yesBtn = document.querySelector(".yes-btn");
let noBtn = document.querySelector(".no-btn");
let gif = document.querySelector("#gif");
let question = document.querySelector("#love");
yesBtn.addEventListener("click",()=> {
    question.innerHTML="I love it too";
    gif.src="tonton2.webp";
});
noBtn.addEventListener("mouseover",()=>{
    console.log("helllo");
 const container = document.querySelector(".container1")
 const containerRect = container.getBoundingClientRect();
 const noBtnRect = noBtn.getBoundingClientRect();

 const maxX = containerRect.width - noBtnRect.width;
 const maxY = containerRect.height - noBtnRect.height;

 const randomX = Math.floor(Math.random() * maxX);
 const randomY = Math.floor(Math.random() * maxY);
  
  noBtn.style.left = randomX + "px";
  noBtn.style.top = randomY + "px";

});








