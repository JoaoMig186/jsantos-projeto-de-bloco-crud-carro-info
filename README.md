# 🚗 Projeto CRUD Carros – Java + Javalin + Gradle

![Build](https://github.com/USUARIO/REPOSITORIO/actions/workflows/build.yml/badge.svg)
![Tests & Coverage](https://github.com/USUARIO/REPOSITORIO/actions/workflows/tests.yml/badge.svg)
![CodeQL](https://github.com/USUARIO/REPOSITORIO/actions/workflows/codeql.yml/badge.svg)
![DAST ZAP Scan](https://github.com/USUARIO/REPOSITORIO/actions/workflows/dast.yml/badge.svg)

Aplicação web desenvolvida em **Java 21**, utilizando **Javalin**, **Gradle** e **Selenium** para testes UI.  
Inclui um pipeline CI/CD completo no GitHub Actions com build, testes, cobertura, SAST, DAST e publicação de artefatos.

---

# ⚙️ **Como rodar localmente**

### 1. Instalar dependências
Requer:
- Java 21+
- Gradle Wrapper incluído no projeto

### 2. Rodar a aplicação

```bash
./gradlew run ```

### 3. Rodar testes
./gradlew test

### 4. Gerar relatório de cobertura
./gradlew jacocoTestReport

build/reports/tests/test/
build/reports/jacoco/test/html/


  
