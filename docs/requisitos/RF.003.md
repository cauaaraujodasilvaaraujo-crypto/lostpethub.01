# RF.003 - Gerenciamento de Status e Desaparecimento do Pet

**COMO** Tutor ou Voluntário,  
**QUERO** atualizar o status do animal (ex: `PERDIDO`, `ENCONTRADO`, `RESGATADO`, `COM_TUTOR`)  
**PARA** alertar a comunidade sobre animais que precisam ser localizados ou informar que já foram resgatados.

---

### Critérios de Aceitação:

1. **Alteração de Estado**: O sistema deve permitir modificar o status do pet entre os valores válidos: `PERDIDO`, `ENCONTRADO`, `RESGATADO`, `COM_TUTOR`.
2. **Alerta de Desaparecimento**: Ao alterar o status de um pet para `PERDIDO`, o sistema deve emitir um log de alerta informando a comunidade.
3. **Filtragem por Status**: O sistema deve permitir listar exclusivamente os animais que estão com status `PERDIDO` ou por espécie.
4. **Validação de ID**: O sistema deve validar que o animal informado existe antes de efetuar qualquer alteração de status.
