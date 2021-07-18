/* ------------------------------------------------
João Cavaco
l42470@alunos.uevora.pt
------------------------------------------

*/

/**
 * "Entra" num espaço de objeto.
 *
 * @param {path} tx - translation x
 * @param {path} ty - translation y
 * @param {path} sx - scale x
 * @param {path} sy - scale y
 *
 */

function enter(c, tx, ty, sx, sy) {
  c.save();
  c.translate(tx, ty);
  c.scale(sx, sy);
}

/**
 * "Retorna" dum espaço de objeto e constrói o objeto no espaço atual.
 *
 * @param {path} c - contexto
 * @param {path} fs - Fill Style
 * @param {path} ss - Stroke Style
 * @param {path} lw - Line Width
 */

function leave(c, fs, ss, lw) {
  c.restore();

  if (fs != 0) {
    c.fillStyle = fs;
    c.fill();
  }
  if (ss != 0) {
    c.strokeStyle = ss;
    c.lineWidth = lw;
    c.stroke();
  }
}

/**
 *  Definição dos contornos das diferentes formas
 *
 *
 */

/**
 * Contorno do pão definido
 */
function bread(c) {
  c.beginPath();
  c.moveTo(0.2678, 0.3809);
  c.bezierCurveTo(0.3511, 0.0119, 0.7916, -0.0178, 1, 0.1845);
  c.quadraticCurveTo(0.6369, 0.244, 0.2678, 0.3809);
  c.closePath();
}

/**
 * Contorno de uma das formas tipo lágrimas que estão contidas no pão,
 */
function teardropA(c) {
  c.beginPath();
  c.moveTo(0.3214, 0.3095);
  c.bezierCurveTo(0.2797, 0.3154, 0.4107, 0.1428, 0.4583, 0.1428);
  c.bezierCurveTo(0.4226, 0.1428, 0.3154, 0.2797, 0.3452, 0.3095);
  c.quadraticCurveTo(0.3154, 0.3333, 0.3125, 0.3035);
  c.closePath();
}

/**
 * Contorno de uma das formas tipo lágrimas que estão contidas no pão,
 */
function teardropB(c) {
  c.beginPath();
  c.moveTo(0.7349, 0.0843);
  c.quadraticCurveTo(1, 0.1566, 0.8493, 0.1927);
  c.quadraticCurveTo(0.9277, 0.1566, 0.7349, 0.0843);
  c.closePath();
}

/**
 * Contorno da letra B
 */
function B(c) {
  c.beginPath();
  c.moveTo(0.2049, 0.68);
  c.lineTo(0.2049, 1);
  c.lineTo(0.3688, 0.9262);
  c.bezierCurveTo(0.4344, 0.9016, 0.4262, 0.7704, 0.3688, 0.7704);
  c.bezierCurveTo(0.4262, 0.7459, 0.4098, 0.5983, 0.3278, 0.6311);
  c.lineTo(0.2049, 0.6803);
  c.moveTo(0.2786, 0.7049);
  c.lineTo(0.2786, 0.7704);
  c.closePath();
}

/**
 * Contorno da letra U
 */
function U(c) {
  c.beginPath();
  c.moveTo(0.4862, 0.6605);
  c.lineTo(0.4862, 0.9266);
  c.quadraticCurveTo(0.5137, 1, 0.5779, 0.9816);
  c.quadraticCurveTo(0.7064, 0.9541, 0.7247, 0.8715);
  c.lineTo(0.7247, 0.5963);
  c.quadraticCurveTo(0.7064, 0.5779, 0.6422, 0.5963);
  c.lineTo(0.6422, 0.8623);
  c.quadraticCurveTo(0.5963, 0.9082, 0.5688, 0.88073);
  c.lineTo(0.5688, 0.6513);
  c.quadraticCurveTo(0.5596, 0.6238, 0.4862, 0.6605);
  c.closePath();
}

/**
 * Contorno da letra R
 */
function R(c) {
  c.beginPath();
  c.moveTo(0.7477, 0.5585);
  c.lineTo(0.7477, 0.8918);
  c.quadraticCurveTo(0.8108, 0.8918, 0.8378, 0.8378);
  c.lineTo(0.8378, 0.7477);
  c.lineTo(0.9279, 0.8288);
  c.quadraticCurveTo(0.9909, 0.8288, 0.9909, 0.7657);
  c.lineTo(0.9189, 0.6936);
  c.quadraticCurveTo(0.9639, 0.6576, 0.9729, 0.6306);
  c.quadraticCurveTo(1, 0.5495, 0.9369, 0.5225);
  c.quadraticCurveTo(0.9099, 0.5045, 0.8558, 0.5225);
  c.closePath();
}

/**
 * Contorno da letra E
 */
function E(c) {
  c.beginPath();
  c.moveTo(0.8674, 0.2771);
  c.lineTo(0.8674, 0.4939);
  c.lineTo(0.9819, 0.4698);
  c.quadraticCurveTo(1.006, 0.4578, 1, 0.4156);
  c.lineTo(0.9277, 0.4337);
  c.lineTo(0.9277, 0.3855);
  c.lineTo(0.9819, 0.3734);
  c.quadraticCurveTo(0.9939, 0.3674, 0.9939, 0.3313);
  c.lineTo(0.9277, 0.3433);
  c.lineTo(0.9277, 0.3072);
  c.lineTo(0.9879, 0.2951);
  c.quadraticCurveTo(1, 0.2891, 0.9939, 0.253);
  c.closePath();
}

/**
 * Contorno da letra K
 */
function K(c) {
  c.beginPath();
  c.moveTo(0.1833, 0.7);
  c.lineTo(0.1833, 1);
  c.lineTo(0.2555, 0.9722);
  c.quadraticCurveTo(0.2722, 0.9666, 0.2722, 0.9388);
  c.lineTo(0.2722, 0.8277);
  c.lineTo(0.3666, 0.9222);
  c.quadraticCurveTo(0.3944, 0.9444, 0.4277, 0.9111);
  c.quadraticCurveTo(0.45, 0.8888, 0.4333, 0.8611);
  c.lineTo(0.35, 0.7833);
  c.lineTo(0.4277, 0.6722);
  c.quadraticCurveTo(0.4444, 0.6388, 0.4166, 0.6166);
  c.quadraticCurveTo(0.3833, 0.6, 0.3611, 0.6277);
  c.lineTo(0.2722, 0.7722);
  c.lineTo(0.2722, 0.6666);
  c.closePath();
}

/**
 * Contorno da letra I
 */
function I(c) {
  c.beginPath();
  c.moveTo(0.5093, 0.6956);
  c.lineTo(0.5093, 1);
  c.lineTo(0.5776, 0.9813);
  c.quadraticCurveTo(0.6086, 0.9689, 0.6149, 0.9378);
  c.lineTo(0.6149, 0.6211);
  c.lineTo(0.5465, 0.6459);
  c.quadraticCurveTo(0.5093, 0.6645, 0.5093, 0.6956);
  c.closePath();
}

/**
 * Contorno da letra N
 */
function N(c) {
  c.beginPath();
  c.moveTo(0.6907, 0.6513);
  c.lineTo(0.6907, 1.0131);
  c.lineTo(0.7565, 0.9868);
  c.quadraticCurveTo(0.7828, 0.9736, 0.7894, 0.9539);
  c.lineTo(0.7894, 0.7894);
  c.lineTo(0.9276, 0.9276);
  c.quadraticCurveTo(0.9539, 0.9407, 0.9802, 0.9276);
  c.quadraticCurveTo(152 / 152, 140 / 152, 153 / 152, 135 / 152);
  c.lineTo(1, 0.5657);
  c.lineTo(0.9539, 0.5789);
  c.quadraticCurveTo(0.9144, 0.5921, 0.9078, 0.6184);
  c.lineTo(0.9078, 0.7763);
  c.lineTo(0.7894, 0.6381);
  c.quadraticCurveTo(0.7697, 0.625, 0.75, 0.6315);
  c.closePath();
}

/**
 * Contorno da letra G
 */
function G(c) {
  c.beginPath();
  c.moveTo(0.8786, 0.4951);
  c.lineTo(0.9854, 0.4708);
  c.lineTo(0.9854, 0.5728);
  c.quadraticCurveTo(0.9854, 0.5873, 0.9757, 0.6019);
  c.quadraticCurveTo(0.9417, 0.6553, 0.8737, 0.6504);
  c.quadraticCurveTo(0.7766, 0.6504, 0.7572, 0.5776);
  c.quadraticCurveTo(0.7475, 0.5291, 0.7572, 0.4902);
  c.quadraticCurveTo(0.7718, 0.4029, 0.8737, 0.3786);
  c.quadraticCurveTo(0.9563, 0.364, 0.9854, 0.4029);
  c.bezierCurveTo(1, 0.4174, 0.9757, 0.4563, 0.9466, 0.4466);
  c.quadraticCurveTo(0.9174, 0.432, 0.8834, 0.4417);
  c.quadraticCurveTo(0.8398, 0.4563, 0.8349, 0.5048);
  c.quadraticCurveTo(0.8252, 0.5679, 0.8689, 0.5825);
  c.quadraticCurveTo(0.9029, 0.5873, 0.9174, 0.5679);
  c.lineTo(0.9174, 0.5388);
  c.quadraticCurveTo(0.8932, 0.5582, 0.8786, 0.5388);
  c.closePath();
}

/**
 * Contorno da letra D, usada para os "buracos" das outra letras
 */
function D(c) {
  c.beginPath();
  c.moveTo(0.2786, 0.7049);
  c.lineTo(0.2786, 0.7704);
  c.bezierCurveTo(0.3524, 0.7459, 0.3278, 0.6721, 0.2786, 0.7049);
  c.closePath();
}

function circ(c) {
  c.beginPath();
  c.arc(1, 0.6603, 0.0377, 0, Math.PI * 2);
  c.closePath();
}

/**
 * Contorno da forma azul que está a volta do logo
 */
function arco(c) {
  c.beginPath();
  c.moveTo(0.9148, 0.668);
  c.lineTo(0.8382, 0.6255);
  c.bezierCurveTo(0.6595, 0.9361, 0.2978, 0.8638, 0.1744, 0.7659);
  c.bezierCurveTo(0, 0.5787, 0, 0.3191, 0.1744, 0.1404);
  c.bezierCurveTo(0.2765, 0.0382, 0.3829, 0, 0.5914, 0.0127);
  c.bezierCurveTo(0.3957, -0.0212, 0.2127, 0.0425, 0.0978, 0.1872);
  c.bezierCurveTo(-0.0425, 0.3531, -0.0127, 0.668, 0.0978, 0.7702);
  c.bezierCurveTo(0.2978, 0.9872, 0.6808, 1, 0.9148, 0.668);
  c.closePath();
}

/**
 *  Agrupa e desenha todas as formas
 */
function draw(c) {
  // FORMAS

  // Circulo azul
  enter(c, 220, 170, 620, 620);
  arco(c);
  leave(c, "#185494", "#185494");

  // mini circulo
  enter(c, 179, 144, 600, 600);
  circ(c);
  leave(c, 0, "red", 5);

  // R dentro do mini circulo com 1 D que serve como "buraco"
  enter(c, 710, 467, 90, 90);
  c.rotate(Math.PI * 2.04);
  R(c);
  leave(c, "red", "");

  enter(c, 749, 419, 150, 150);
  c.rotate(Math.PI * 2.04);
  D(c);
  leave(c, "white", "");

  // pão de cima
  enter(c, 200, 176, 450, 450);
  bread(c);
  leave(c, "#faaf18", "#faaf18");

  // primeira  forma branca do pão de cima
  enter(c, 200, 180, 450, 450);
  teardropA(c);
  leave(c, "white", "");
  // segunda forma branca dentro do pão de cima
  enter(c, 200, 176, 450, 450);
  teardropB(c);
  leave(c, "white", "");

  // pão de baixo
  enter(c, 826, 706, 450, 450);
  c.rotate(Math.PI * 1.005);
  bread(c);
  leave(c, "#faaf18", "#faaf18");

  // forma branca no pão de baixo
  enter(c, 826, 706, 450, 450);
  c.rotate(Math.PI * 1.005);
  teardropA(c);
  leave(c, "white", "");

  //LETRAS

  // Letra B com 2 letras D que servem como os "buracos"
  enter(c, 200, 200, 300, 300);
  B(c);
  leave(c, "red", "");
  enter(c, 200, 200, 300, 300);
  D(c);
  leave(c, "white", "white");
  enter(c, 187, 200, 350, 350);
  D(c);
  leave(c, "white", "white");

  // Letra U
  enter(c, 196, 190, 280, 280);
  U(c);
  leave(c, "red", "");
  // Letra R com 1 D que serve como o "buraco"
  enter(c, 185, 177, 300, 300);
  R(c);
  leave(c, "red", "");

  enter(c, 325, 74, 400, 400);
  D(c);
  leave(c, "white", "white");

  // Letra G
  enter(c, 195, 170, 380, 380);
  c.rotate(Math.PI * -2.01);
  G(c);
  leave(c, "red", "");

  // Letra E
  enter(c, 203, 172, 440, 440);
  E(c);
  leave(c, "red", "");

  // Letra R  numero 2
  enter(c, 433, 100, 300, 300);
  c.rotate(0.01 * Math.PI);
  R(c);
  leave(c, "red", "");

  enter(c, 575, 2, 400, 400);
  c.rotate(0.01 * Math.PI);
  D(c);
  leave(c, "white", "white");

  // Letra K
  enter(c, 213, 200, 430, 430);
  K(c);
  leave(c, "red", "white", 5);

  // Letra I
  enter(c, 205, 194, 400, 400);
  I(c);
  leave(c, "red", "");

  // Letra N
  enter(c, 190, 176, 400, 400);
  N(c);
  leave(c, "red", "");

  // Letra G numero 2
  enter(c, 215, 190, 520, 520);
  G(c);
  leave(c, "red", "");
}

function main() {
  var c = document.getElementById("canvas").getContext("2d");
  draw(c);
}
