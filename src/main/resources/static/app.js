const API_URL = '/alimentos';

const form = document.getElementById('alimento-form');
const inputId = document.getElementById('alimento-id');
const inputAlimento = document.getElementById('alimento');
const inputCalorias = document.getElementById('calorias');
const inputEhNatural = document.getElementById('ehNatural');

const btnSubmit = document.getElementById('btn-submit');
const btnCancel = document.getElementById('btn-cancel');
const formTitle = document.getElementById('form-title');
const tabelaCorpo = document.getElementById('tabela-corpo');
const messageContainer = document.getElementById('message-container');

document.addEventListener('DOMContentLoaded', carregarAlimentos);

form.addEventListener('submit', function (e) {
    e.preventDefault();

    const id = inputId.value;
    const valorCaloriasTexto = inputCalorias.value.toString().replace(',', '.');
    const caloriasFormatadas = parseFloat(valorCaloriasTexto);

    if (isNaN(caloriasFormatadas)) {
        exibirMensagem('Insira um valor numérico válido para calorias.', 'error');
        return;
    }

    const alimentoData = {
        alimento: inputAlimento.value,
        calorias: caloriasFormatadas,
        natural: inputEhNatural.checked
    };

    if (id) {
        atualizarAlimento(id, alimentoData);
    } else {
        cadastrarAlimento(alimentoData);
    }
});

btnCancel.addEventListener('click', limparFormulario);

function carregarAlimentos() {
    fetch(API_URL)
        .then(async response => {
            if (!response.ok) {
                const erroTxt = await response.text();
                throw new Error(`Erro ${response.status}: ${erroTxt || 'Falha ao buscar alimentos'}`);
            }
            return response.json();
        })
        .then(alimentos => {
            renderizarTabela(alimentos);
        })
        .catch(error => {
            console.error(error);
            exibirMensagem(error.message, 'error');
            tabelaCorpo.innerHTML = `<tr><td colspan="5" class="loading">Erro ao carregar dados.</td></tr>`;
        });
}

function cadastrarAlimento(alimento) {
    fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(alimento)
    })
        .then(async response => {
            if (!response.ok) {
                const erroTxt = await response.text();
                throw new Error(`Erro ${response.status}: ${erroTxt || 'Erro ao cadastrar'}`);
            }
            exibirMensagem('Alimento cadastrado com sucesso!', 'success');
            limparFormulario();
            carregarAlimentos();
        })
        .catch(error => {
            console.error(error);
            exibirMensagem(error.message, 'error');
        });
}

function atualizarAlimento(id, alimento) {
    fetch(`${API_URL}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(alimento)
    })
        .then(async response => {
            if (!response.ok) throw new Error('Erro ao atualizar alimento.');
            exibirMensagem('Alimento atualizado com sucesso!', 'success');
            limparFormulario();
            carregarAlimentos();
        })
        .catch(error => exibirMensagem(error.message, 'error'));
}

function deletarAlimento(id) {
    if (confirm('Tem certeza que deseja excluir este alimento?')) {
        fetch(`${API_URL}/${id}`, { method: 'DELETE' })
            .then(response => {
                if (!response.ok) throw new Error('Erro ao deletar alimento.');
                exibirMensagem('Alimento removido com sucesso!', 'success');
                carregarAlimentos();
            })
            .catch(error => exibirMensagem(error.message, 'error'));
    }
}

function renderizarTabela(alimentos) {
    tabelaCorpo.innerHTML = '';

    if (!Array.isArray(alimentos) || alimentos.length === 0) {
        tabelaCorpo.innerHTML = `<tr><td colspan="5" class="loading">Nenhum alimento cadastrado.</td></tr>`;
        return;
    }

    alimentos.forEach(item => {
        const isNatural = item.natural !== undefined ? item.natural : (item.ehNatural !== undefined ? item.ehNatural : item['eh-natural']);
        const tagNatural = isNatural ? 'Sim 🍃' : 'Não 🏭';
        const nomeAlimento = item.alimento || item.nome || '';

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${item.id}</td>
            <td><strong>${nomeAlimento}</strong></td>
            <td>${item.calorias} kcal</td>
            <td>${tagNatural}</td>
            <td class="actions-cell">
                <button class="btn btn-edit" onclick="prepararEdicao(${item.id}, '${nomeAlimento}', ${item.calorias}, ${isNatural})">Editar</button>
                <button class="btn btn-delete" onclick="deletarAlimento(${item.id})">Excluir</button>
            </td>
        `;
        tabelaCorpo.appendChild(tr);
    });
}

function prepararEdicao(id, alimento, calorias, ehNatural) {
    inputId.value = id;
    inputAlimento.value = alimento;
    inputCalorias.value = calorias;
    inputEhNatural.checked = Boolean(ehNatural);

    formTitle.innerText = 'Editar Alimento';
    btnSubmit.innerText = 'Atualizar Alimento';
    btnCancel.classList.remove('hidden');
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

function limparFormulario() {
    inputId.value = '';
    form.reset();
    formTitle.innerText = 'Cadastrar Novo Alimento';
    btnSubmit.innerText = 'Salvar Alimento';
    btnCancel.classList.add('hidden');
}

function exibirMensagem(texto, tipo) {
    messageContainer.innerText = texto;
    messageContainer.className = `message ${tipo}`;
    setTimeout(() => {
        messageContainer.className = 'message hidden';
    }, 4000);
}