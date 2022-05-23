import React, { useState } from "react";
import ReactDOM from "react-dom";
import Wheel from "./components/wheel";

import "./styles.css";

export default function App() {
  const [valorRueda, setValorRueda] = useState();
  const places = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"];
  const position = [1, 2, 3, 4, 5];
  const [pc, setPc] = useState(["■", "■", "■", "■", "■"]);
  const [player, setPlayer] = useState(["■", "■", "■", "■", "■"]);
  let [contPc, setContPc] = useState(0);
  const matrizEstrategia = [
    [5, 4, 3, 2, 1],
    [5, 3, 3, 2, 1],
    [5, 3, 3, 2, 1],
    [4, 3, 2, 2, 1],
    [4, 3, 2, 2, 1],
    [3, 2, 2, 1, 1],
    [3, 2, 1, 1, 1],
    [2, 2, 1, 1, 1],
    [1, 1, 1, 1, 1],
    [1, 1, 1, 1, 1]
  ];

  const handleClick = (value) => {
    switch (value) {
      case 1:
        if (valorRueda == null) {
          alert("Gire la rueda primero");
          break;
        }
        if (player[0] === "■") {
          player[0] = valorRueda;
          setPlayer(player);
          pcEstrategia(contPc);
          contPc++;
          setContPc(contPc);
        } else {
          alert("Esta casilla ya está ocupada");
        }
        break;
      case 2:
        if (valorRueda == null) {
          alert("Gire la rueda primero");
          break;
        }
        if (player[1] === "■") {
          player[1] = valorRueda;
          setPlayer(player);
          pcEstrategia(contPc);
          contPc++;
          setContPc(contPc);
        } else {
          alert("Esta casilla ya está ocupada");
        }
        break;
      case 3:
        if (valorRueda == null) {
          alert("Gire la rueda primero");
          break;
        }
        if (player[2] === "■") {
          player[2] = valorRueda;
          setPlayer(player);
          pcEstrategia(contPc);
          contPc++;
          setContPc(contPc);
        } else {
          alert("Esta casilla ya está ocupada");
        }
        break;
      case 4:
        if (valorRueda == null) {
          alert("Gire la rueda primero");
          break;
        }
        if (player[3] === "■") {
          player[3] = valorRueda;
          setPlayer(player);
          pcEstrategia(contPc);
          contPc++;
          setContPc(contPc);
        } else {
          alert("Esta casilla ya está ocupada");
        }
        break;
      case 5:
        if (valorRueda == null) {
          alert("Gire la rueda primero");
          break;
        }
        if (player[4] === "■") {
          player[4] = valorRueda;
          setPlayer(player);
          pcEstrategia(contPc);
          contPc++;
          setContPc(contPc);
        } else {
          msjAlert("Esta casilla ya está ocupada");
        }
        break;
      default:
    }
  };
  const pcEstrategia = (contPc) => {
    let pos = matrizEstrategia[valorRueda][contPc];
    let casillasVacias = 0;
    for (var i = 0; i < pc.length; i++) {
      if (pc[i] === "■") {
        casillasVacias++;
      }
      if (pos === casillasVacias) {
        pc[i] = valorRueda;
        setPc(pc);
        break;
      }
    }
  };
  const resultado = () => {
    if (pc > player) alert("Gana pc");
    else if (player > pc) alert("Gana player");
    else alert("Empate");
  };
  const msjAlert = (msj) => {
    msj.Dialog();
  };
  return (
    <div className="App">
      <h1>SO WHO’S COUNTING</h1>
      <h2>Mateo Lis Peña</h2>
      <h1>El resultado es: {valorRueda}</h1>
      <Wheel items={places} setValorRueda={setValorRueda} />
      <div className="game">
        <h2>Usuario</h2>
        {player.map((choice, index) => (
          <li key={index}>{choice}</li>
        ))}
        <br></br>
        {position.map((choice, index) => (
          <button key={index} onClick={() => handleClick(choice)}>
            {choice}
          </button>
        ))}
        <h1>PC</h1>
        {pc.map((choice, index) => (
          <li key={index}>{choice}</li>
        ))}
      </div>
    </div>
  );
}
const rootElement = document.getElementById("root");
ReactDOM.render(<App />, rootElement);
