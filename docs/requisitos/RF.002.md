# RF.002 - Cadastro de Animais de Estimação (Pets)

**COMO** Tutor cadastrado no sistema,  
**QUERO** cadastrar meu animal de estimação (Pet) associado ao meu perfil  
**PARA** manter o registro das características do animal (nome, espécie, raça, cor, porte).

---

### Critérios de Aceitação:

1. **Campos Obrigatórios**: O sistema deve exigir o **Nome**, **Espécie** (ex: Cão, Gato, Ave), **Cor/Características** e o vínculo obrigatório com um **Tutor existente**.
2. **Status Inicial Padrão**: Todo pet recém-cadastrado deve iniciar por padrão com o status `COM_TUTOR`.
3. **Validação de Existência do Tutor**: O sistema deve impedir o cadastro de um pet vinculado a um `tutor_id` inexistente no banco de dados.
4. **Listagem e Consulta**: O sistema deve permitir consultar todos os pets cadastrados de um tutor específico.
