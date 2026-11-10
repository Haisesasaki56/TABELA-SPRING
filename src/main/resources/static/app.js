const API_URL = "http://localhost:8080/alimentos";

// Elementos do DOM
const form = document.getElementById("alimento-form");
const inputId = document.getElementById("alimento-id");
const inputNome = document.getElementById("alimento");
const inputCalorias = document.getElementById("calorias");
const inputEhNatural = document.getElementById("ehNatural");
const tabelaCorpo = document.getElementById("tabela-corpo");
const messageContainer = document.getElementById("message-container");
const btnCancel = document.getElementById("btn-cancel");
const formTitle = document.getElementById("form-title");

// Elementos de Busca
const searchInput = document.getElementById('search-input');
const btnSearch = document.getElementById('btn-search');
const btnClear = document.getElementById('btn-clear');

// Inicialização
document.addEventListener("DOMContentLoaded", carregarAlimentos);

// Função para exibir mensagens na tela
function mostrarMensagem(texto, tipo) {
    messageContainer.textContent = texto;
    messageContainer.className = `message ${tipo}`;
    messageContainer.classList.remove("hidden");

    setTimeout(() => {
        messageContainer.classList.add("hidden");
    }, 4000);
}

// Carregar todos os alimentos
async function carregarAlimentos() {
    try {
        const response = await fetch(API_URL);
        const alimentos = await response.json();
        renderizarTabela(alimentos);
    } catch (error) {
        console.error("Erro ao carregar alimentos:", error);
        mostrarMensagem("Erro de conexão com o servidor.", "error");
    }
}

// Renderizar os itens na tabela HTML
function renderizarTabela(alimentos) {
    tabelaCorpo.innerHTML = "";

    if (!alimentos || alimentos.length === 0) {
        tabelaCorpo.innerHTML = `<tr><td colspan="5" style="text-align: center; color: var(--text-muted);">Nenhum alimento encontrado.</td></tr>`;
        return;
    }

    alimentos.forEach(alimento => {
        const tr = document.createElement("tr");

        const isNaturalText = alimento.ehNatural ?
            `<span style="color: #38A169; font-weight: 600;">✓ Sim</span>` :
            `<span style="color: #718096;">✗ Não</span>`;

        tr.innerHTML = `
            <td>#${alimento.id}</td>
            <td style="font-weight: 500;">${alimento.alimento}</td>
            <td>${alimento.calorias} kcal</td>
            <td>${isNaturalText}</td>
            <td class="actions-cell">
                <button class="btn btn-edit" onclick="editarAlimento(${alimento.id}, '${alimento.alimento}', ${alimento.calorias}, ${alimento.ehNatural})">Editar</button>
                <button class="btn btn-delete" onclick="deletarAlimento(${alimento.id})">Excluir</button>
            </td>
        `;
        tabelaCorpo.appendChild(tr);
    });
}

// Salvar (Cadastrar ou Atualizar)
form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const alimentoObj = {
        alimento: inputNome.value.trim(),
        calorias: parseFloat(inputCalorias.value),
        ehNatural: inputEhNatural.checked
    };

    const id = inputId.value;
    const isEdit = !!id;
    const url = isEdit ? `${API_URL}/${id}` : API_URL;
    const method = isEdit ? "PUT" : "POST";

    try {
        const response = await fetch(url, {
            method: method,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(alimentoObj)
        });

        if (response.ok) {
            mostrarMensagem(isEdit ? "Alimento atualizado!" : "Alimento cadastrado com sucesso!", "success");
            resetarFormulario();
            carregarAlimentos();

            // Limpa a busca ao salvar algo novo/editar para ver a lista atualizada
            if(!btnClear.classList.contains('hidden')) {
                btnClear.click();
            }
        } else {
            mostrarMensagem("Erro ao salvar alimento.", "error");
        }
    } catch (error) {
        console.error("Erro ao salvar:", error);
        mostrarMensagem("Erro de conexão.", "error");
    }
});

// Preparar formulário para edição
window.editarAlimento = function(id, nome, calorias, ehNatural) {
    inputId.value = id;
    inputNome.value = nome;
    inputCalorias.value = calorias;
    inputEhNatural.checked = ehNatural;

    formTitle.textContent = "Editar Alimento";
    btnCancel.classList.remove("hidden");
    window.scrollTo({ top: 0, behavior: 'smooth' });
};

// Cancelar Edição
btnCancel.addEventListener("click", resetarFormulario);

function resetarFormulario() {
    form.reset();
    inputId.value = "";
    formTitle.textContent = "Cadastrar Novo Alimento";
    btnCancel.classList.add("hidden");
}

// Excluir Alimento
window.deletarAlimento = async function(id) {
    if (!confirm("Tem certeza que deseja excluir este alimento?")) return;

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: "DELETE"
        });

        if (response.ok) {
            mostrarMensagem("Alimento excluído.", "success");
            carregarAlimentos();
        } else {
            mostrarMensagem("Erro ao excluir.", "error");
        }
    } catch (error) {
        console.error("Erro ao excluir:", error);
        mostrarMensagem("Erro de conexão.", "error");
    }
};

// ==========================================
// NOVA LÓGICA DE BUSCA
// ==========================================
btnSearch.addEventListener('click', async () => {
    const termo = searchInput.value.trim();

    if (!termo) {
        mostrarMensagem("Digite o nome de um alimento para buscar.", "error");
        return;
    }

    try {
        const response = await fetch(`${API_URL}/buscar?alimento=${encodeURIComponent(termo)}`);

        if (response.ok) {
            const alimentoEncontrado = await response.json();
            renderizarTabela([alimentoEncontrado]); // Renderiza apenas o item encontrado
            btnClear.classList.remove('hidden');
        } else if (response.status === 404 || response.status === 204) {
            renderizarTabela([]); // Esvazia a tabela
            mostrarMensagem("Nenhum alimento encontrado com este nome.", "error");
            btnClear.classList.remove('hidden');
        }
    } catch (error) {
        console.error("Erro na busca:", error);
        mostrarMensagem("Erro ao buscar o alimento.", "error");
    }
});

// Limpar Busca
btnClear.addEventListener('click', () => {
    searchInput.value = '';
    btnClear.classList.add('hidden');
    carregarAlimentos(); // Recarrega a lista original
});

// Busca ao pressionar "Enter"
searchInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        e.preventDefault();
        btnSearch.click();
    }
});