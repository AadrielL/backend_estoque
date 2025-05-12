const API_URL = "http://localhost:8080/estoque";

// Utilitário para mostrar mensagens
function mostrarMensagem(texto, erro = false) {
    const mensagem = document.getElementById('mensagem');
    if (mensagem) {
        mensagem.textContent = texto;
        mensagem.style.color = erro ? 'red' : 'green';
        setTimeout(() => mensagem.textContent = '', 3500);
    }
}

// Checa autenticação e exibe usuário logado
async function checarAutenticacao() {
    try {
        const resp = await fetch("/usuario/perfil");
        if (!resp.ok) {
            window.location.href = "login.html";
            return false;
        }
        const usuario = await resp.json();
        window.usuarioRole = usuario.role;
        const userInfo = document.getElementById("userInfo");
        if (userInfo) {
            userInfo.textContent = `Usuário: ${usuario.username} | Papel: ${usuario.role}`;
        }
        document.querySelectorAll('.admin-only').forEach(el => {
            el.style.display = (usuario.role === "ADMIN") ? '' : 'none';
        });
        return true;
    } catch (e) {
        window.location.href = "login.html";
        return false;
    }
}

// Utilitário para pegar parâmetros da URL
function getParams() {
    return new URLSearchParams(window.location.search);
}

// ----------- INDEX (cards) -----------
function indexPageInit() {
    checarAutenticacao();
    document.querySelectorAll('.hospital-card').forEach(function(card) {
        card.addEventListener('click', function(e) {
            if (e.target.classList.contains('controle-btn')) return; // Não redireciona se for o botão
            const nome = card.getAttribute('data-hospital');
            window.location.href = `estoque.html?hospital=${encodeURIComponent(nome)}`;
        });
    });
}

// ----------- ADICIONAR/RETIRAR ITEM -----------
function adicionarPageInit() {
    checarAutenticacao();

    const params = getParams();
    const hospitalFromUrl = params.get('hospital');
    const form = document.getElementById('formAdicionar');
    const hospitalSelect = document.getElementById('hospital');
    const hospitalDiv = document.getElementById('hospitalSelectDiv');
    const categoriaInput = document.getElementById('categoria');
    const operacaoInput = document.getElementById('operacao');
    const quantidadeInput = document.getElementById('quantidade');

    // Se veio hospital pela URL, setar e esconder o select
    if (hospitalFromUrl) {
        hospitalSelect.value = hospitalFromUrl;
        hospitalDiv.style.display = 'none';
    }

    form.addEventListener('submit', async function (e) {
        e.preventDefault();

        const hospital = hospitalSelect.value;
        const categoria = categoriaInput.value;
        const operacao = operacaoInput.value;
        const quantidade = parseInt(quantidadeInput.value);

        if (!hospital || !categoria || !operacao || isNaN(quantidade) || quantidade <= 0) {
            mostrarMensagem('Preencha todos os campos corretamente!', true);
            return;
        }

        try {
            let resp, msg;
            if (operacao === 'subtrair') {
                // Verifica se tem estoque suficiente antes de subtrair
                const qtdAtualResp = await fetch(`${API_URL}/buscar?categoria=${encodeURIComponent(categoria)}&hospital=${encodeURIComponent(hospital)}`);
                let qtdAtual = 0;
                if (qtdAtualResp.ok) {
                    const item = await qtdAtualResp.json();
                    qtdAtual = item.quantidade || 0;
                }
                if (quantidade > qtdAtual) {
                    mostrarMensagem(`Não é possível retirar ${quantidade} se só há ${qtdAtual} disponível!`, true);
                    return;
                }
                resp = await fetch(`${API_URL}/subtrair?categoria=${encodeURIComponent(categoria)}&quantidade=${quantidade}&hospital=${encodeURIComponent(hospital)}`, {
                    method: 'PUT'
                });
                msg = await resp.text();
            } else {
                resp = await fetch(`${API_URL}/somar?categoria=${encodeURIComponent(categoria)}&quantidade=${quantidade}&hospital=${encodeURIComponent(hospital)}`, {
                    method: 'PUT'
                });
                msg = await resp.text();
            }

            if (resp.ok) {
                mostrarMensagem(msg, false);
                form.reset();
            } else {
                mostrarMensagem(msg, true);
            }

            if (hospitalFromUrl) {
                setTimeout(() => {
                    window.location.href = `estoque.html?hospital=${encodeURIComponent(hospital)}`;
                }, 1500);
            }

        } catch (error) {
            console.error('Erro:', error);
            mostrarMensagem('Erro de conexão.', true);
        }
    });
}

// ----------- LISTAR ESTOQUE POR HOSPITAL -----------
async function carregarEstoque() {
    await checarAutenticacao();
    const params = getParams();
    const hospital = params.get('hospital');
    document.getElementById('tituloEstoque').textContent = `Estoque - ${hospital}`;
    const tbody = document.getElementById('tabelaItens');
    tbody.innerHTML = '';

    try {
        const resp = await fetch(`${API_URL}/hospital?hospital=${encodeURIComponent(hospital)}`);
        if (resp.ok) {
            const itens = await resp.json();
            itens.forEach(item => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${item.categoria}</td>
                    <td>${item.quantidade}</td>
                `;
                tbody.appendChild(tr);
            });
        } else {
            tbody.innerHTML = '<tr><td colspan="2">Erro ao buscar estoque</td></tr>';
        }
    } catch (e) {
        tbody.innerHTML = '<tr><td colspan="2">Erro de conexão</td></tr>';
    }
}

// ----------- INICIALIZAÇÃO -----------
document.addEventListener('DOMContentLoaded', function() {
    if (document.getElementById('formAdicionar')) {
        adicionarPageInit();
    }
    if (document.getElementById('tabelaItens')) {
        carregarEstoque();
    }
    if (document.getElementById('hospitais-container')) {
        indexPageInit();
    }
});