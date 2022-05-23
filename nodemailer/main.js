var nodemailer = require("nodemailer");
var express = require("express");
var path = require("path");
var app = express();
var correos = "mateolis03@gmail.com";


app.get("/", function(request, response) {
    response.sendFile(path.join(__dirname, 'views/index.html'));
});

app.post("/send-email/", (req, res) => {
    const transporter = nodemailer.createTransport({
        service: 'gmail',
        auth: {
            user: 'pruebaweb231@gmail.com',
            pass: 'Pruebaweb123'
        }
    });
    var mail = {
        from: '"Laura, Mateo y Andrés" <pruebaweb231@gmail.com>',
        to: correos,
        subject: "Prueba de NodeJs ✔ 👨🏻‍💻🐱",
        html: `<b>Como podemos comprobar, es posible el envío efectivo de correos con NodeJs y NodeMailer!</b> 
        <p></p>     
        <img align="center" src="https://media.giphy.com/media/MDJ9IbxxvDUQM/giphy.gif">
        <p></p>
        <b>Gracias por su atención!</b> `
    }
    transporter.sendMail(mail, (error, info) => {
        if (error) {
            res.status(500).send(error.message);
        } else {
            console.log("Email Enviado.");
            res.status(204).jsonp(req.body);
        }
    });
});

app.listen(3000, () => {
    console.log("Servidor en => http://localhost:3000");
});