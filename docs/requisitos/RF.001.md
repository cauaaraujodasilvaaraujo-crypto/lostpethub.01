# RF.001 - Cadastro de Tutor / Responsável

**COMO** Usuário ou Tutor de um animal de estimação,  
**QUERO** cadastrar meus dados de contato no sistema LostPetHub  
**PARA** que as pessoas que encontrarem meu animal perdido possam entrar em contato comigo rapidamente.

---

### Critérios de Aceitação:

1. **Campos Obrigatórios**: O sistema deve exigir o preenchimento do **Nome**, **Telefone/WhatsApp** e **E-mail** do tutor.
2. **Unicidade de E-mail**: Não deve ser permitido o cadastro de dois tutores com o mesmo endereço de e-mail.
3. **Validação de Formato**: O e-mail informado deve possuir um formato válido (ex: `usuario@dominio.com`).
4. **Persistência**: Ao cadastrar com sucesso, os dados devem ser gravados permanentemente no banco de dados com um identificador único (`id`).
