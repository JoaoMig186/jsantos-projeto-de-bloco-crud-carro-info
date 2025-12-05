# 🔧 Configuração de Ambientes de Deploy

Este guia explica como configurar os ambientes de deploy (DEV, TEST, PROD) com regras de proteção no GitHub.

## 📋 Estrutura de Ambientes

### 🟢 Development (DEV)
- **Branch**: `develop` ou `dev`
- **Deploy**: Automático em push
- **Aprovação**: Não requerida
- **Uso**: Desenvolvimento contínuo, testes rápidos

### 🟡 Test/Staging (TEST)
- **Branch**: `test` ou `staging`
- **Deploy**: Automático em push (após testes)
- **Aprovação**: Opcional (configurável)
- **Uso**: Testes de integração, validação antes de produção

### 🔴 Production (PROD)
- **Branch**: `master` ou `main`
- **Deploy**: Manual via `workflow_dispatch` ou automático em push
- **Aprovação**: **OBRIGATÓRIA** (configurável)
- **Uso**: Ambiente de produção, usuários finais

## ⚙️ Configurando Ambientes no GitHub

### 1. Acessar Configurações de Ambiente

1. Vá em **Settings** > **Environments**
2. Clique em **New environment**
3. Crie os três ambientes: `development`, `test`, `production`

### 2. Configurar Ambiente DEVELOPMENT

**Nome**: `development`

**Protection rules** (opcional para DEV):
- ⬜ Required reviewers: **Desabilitado** (deploy automático)
- ⬜ Wait timer: **0 minutos**
- ⬜ Deployment branches: **Permitir todas**

**Secrets** (Settings > Environments > development > Secrets):
- `DEV_SSH_HOST`: Endereço do servidor DEV
- `DEV_SSH_USER`: Usuário SSH
- `DEV_SSH_KEY`: Chave privada SSH
- `DEV_SSH_PORT`: Porta SSH (opcional, padrão: 22)
- `DEV_DEPLOY_PATH`: Caminho de deploy (ex: `/var/www/javalin-dev`)
- `DEV_URL`: URL do ambiente (ex: `http://dev.example.com`)

### 3. Configurar Ambiente TEST

**Nome**: `test`

**Protection rules** (recomendado):
- ☑️ Required reviewers: **1 reviewer** (opcional, mas recomendado)
- ☑️ Wait timer: **5 minutos** (dar tempo para verificação)
- ☑️ Deployment branches: **Apenas branches específicas**
  - Adicionar: `test`, `staging`

**Secrets**:
- `TEST_SSH_HOST`: Endereço do servidor TEST
- `TEST_SSH_USER`: Usuário SSH
- `TEST_SSH_KEY`: Chave privada SSH
- `TEST_SSH_PORT`: Porta SSH (opcional)
- `TEST_DEPLOY_PATH`: Caminho de deploy (ex: `/var/www/javalin-test`)
- `TEST_URL`: URL do ambiente (ex: `http://test.example.com`)

### 4. Configurar Ambiente PRODUCTION

**Nome**: `production`

**Protection rules** (OBRIGATÓRIO):
- ☑️ Required reviewers: **2 reviewers** (mínimo recomendado)
- ☑️ Wait timer: **10 minutos** (tempo para revisão)
- ☑️ Deployment branches: **Apenas branches específicas**
  - Adicionar: `master`, `main`
- ☑️ Prevent self-review: **Habilitado** (não pode aprovar próprio deploy)

**Secrets**:
- `PROD_SSH_HOST`: Endereço do servidor PROD
- `PROD_SSH_USER`: Usuário SSH
- `PROD_SSH_KEY`: Chave privada SSH
- `PROD_SSH_PORT`: Porta SSH (opcional)
- `PROD_DEPLOY_PATH`: Caminho de deploy (ex: `/var/www/javalin`)
- `PROD_URL`: URL do ambiente (ex: `https://prod.example.com`)

## 🔐 Configurando Secrets por Ambiente

### Opção 1: Secrets por Ambiente (Recomendado)

Cada ambiente tem seus próprios secrets:
- Mais seguro
- Permite diferentes servidores/credenciais por ambiente
- Isolamento completo

**Como configurar**:
1. Settings > Environments > [nome-do-ambiente] > Secrets
2. Adicionar secrets específicos do ambiente

### Opção 2: Secrets Globais (Alternativa)

Se todos os ambientes usam o mesmo servidor com diferentes paths:
- Settings > Secrets and variables > Actions
- Usar variáveis de ambiente no workflow para diferenciar

## 🚀 Fluxo de Deploy

### Deploy Automático DEV
```bash
git checkout develop
git push origin develop
# Deploy automático inicia imediatamente
```

### Deploy TEST
```bash
git checkout test
git merge develop
git push origin test
# Deploy inicia após testes passarem
# Pode requerer aprovação se configurado
```

### Deploy PRODUCTION

**Opção 1: Manual (Recomendado)**
1. Ir em **Actions** > **Deploy - Production**
2. Clicar em **Run workflow**
3. Preencher versão (ex: `1.0.0`)
4. Aguardar aprovação dos reviewers
5. Deploy executa após aprovação

**Opção 2: Automático (Menos seguro)**
```bash
git checkout master
git merge test
git push origin master
# Deploy inicia após validações
# Requer aprovação obrigatória
```

## 📊 Monitoramento de Deploys

### Verificar Status
- **Actions** > Selecionar workflow > Ver logs
- **Environments** > Ver histórico de deploys

### Notificações
- GitHub envia notificações para reviewers
- Email/Slack pode ser configurado via webhooks

## 🔄 Rollback

### Rollback Automático
Os workflows incluem rollback automático se:
- Aplicação não iniciar
- Health check falhar
- Erro durante deploy

### Rollback Manual
```bash
# No servidor
cd /var/www/javalin
# Restaurar backup
cp backups/YYYYMMDD_HHMMSS/app.jar app.jar
# Reiniciar aplicação
pkill -f "java.*app.jar"
nohup java -jar app.jar > app.log 2>&1 &
```

## 🛡️ Regras de Proteção Recomendadas

### Branch Protection Rules

**Para `master/main`**:
- ☑️ Require pull request reviews (2 aprovações)
- ☑️ Require status checks to pass
- ☑️ Require branches to be up to date
- ☑️ Do not allow bypassing
- ☑️ Restrict who can push

**Para `test/staging`**:
- ☑️ Require pull request reviews (1 aprovação)
- ☑️ Require status checks to pass
- ⬜ Do not allow bypassing (opcional)

**Para `develop/dev`**:
- ⬜ Require pull request reviews (deploy rápido)
- ☑️ Require status checks to pass
- ⬜ Do not allow bypassing

## 📝 Checklist de Configuração

- [ ] Criar branch `develop` ou `dev`
- [ ] Criar branch `test` ou `staging`
- [ ] Configurar ambiente `development` no GitHub
- [ ] Configurar ambiente `test` no GitHub
- [ ] Configurar ambiente `production` no GitHub
- [ ] Adicionar secrets para cada ambiente
- [ ] Configurar protection rules para produção
- [ ] Testar deploy em DEV
- [ ] Testar deploy em TEST
- [ ] Testar deploy em PROD (com aprovação)

## 🔍 Troubleshooting

### Deploy não inicia
- Verificar se branch está correta
- Verificar se workflow está ativo
- Verificar logs em Actions

### Aprovação não aparece
- Verificar se environment protection está habilitado
- Verificar se usuário tem permissão de review
- Verificar se wait timer expirou

### Erro de conexão SSH
- Verificar secrets do ambiente
- Verificar se servidor está acessível
- Verificar chave SSH

### Rollback automático
- Verificar logs do servidor
- Verificar se aplicação está respondendo
- Verificar se health check está configurado corretamente

## 📚 Recursos Adicionais

- [GitHub Environments](https://docs.github.com/en/actions/deployment/targeting-different-environments/using-environments-for-deployment)
- [Environment Protection Rules](https://docs.github.com/en/actions/deployment/targeting-different-environments/using-environments-for-deployment#deployment-protection-rules)
- [Branch Protection](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/managing-protected-branches)

