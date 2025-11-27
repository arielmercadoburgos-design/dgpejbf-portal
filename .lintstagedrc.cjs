module.exports = {
  '{,**/}*.{md,json,yml,js,cjs,mjs,ts,cts,mts,java,html,css,scss}': ['prettier --write'],
};
export default {
  '*.{md,json,yml,js,cjs,mjs,ts,cts,mts,html,css,scss}': ['prettier --write'],
  // excluir archivos que no se deben formatear
  '!package-lock.json': [],
  '!**/package-lock.json': [],
};
