import React, { useReducer } from 'react';

const initialState = {
    questions: [
        {
            id: 1,
            question: 'Thủ đô của Úc là thành phố nào?',
            options: ['Sydney', 'Canberra', 'Melbourne', 'Perth'],
            answer: 'Canberra',
        },
        {
            id: 2,
            question: 'Hành tinh nào được biết đến với cái tên "Hành tinh Đỏ"?',
            options: ['Venus', 'Mars', 'Jupiter', 'Saturn'],
            answer: 'Mars',
        },
        {
            id: 3,
            question: 'Hồ nào có quái vật khủng Long"?',
            options: ['Hồ Tây', 'Hồ Hoàn Kiếm', 'Hồ Gươm', 'LockNesh'],
            answer: 'LockNesh',
        },

        // Thêm các câu hỏi khác ở đây
    ],
    currentQuestion: 0,
    selectedOption: '',
    score: 0,
    showScore: false,
};

const quizReducer = (state, action) => {
    switch (action.type) {
        case 'CHON_LUA':
            return {
                ...state,
                selectedOption: action.payload,
            };
        case 'CAU_HOI_TIEP_THEO':
            if (!state.selectedOption) {
                alert('Vui lòng chọn một đáp án trước khi tiếp tục!');
                return state;
            }
            const isCorrect = state.questions[state.currentQuestion].answer === state.selectedOption;
            const nextQuestion = state.currentQuestion + 1;
            let showScore = state.showScore;
            if (nextQuestion >= state.questions.length) {
                showScore = true;
            }
            return {
                ...state,
                currentQuestion: nextQuestion,
                score: isCorrect ? state.score + 1 : state.score,
                selectedOption: '',
                showScore: showScore,
            };
        case 'KHOI_DONG_LAI':
            return initialState;
        default:
            throw new Error('Loại hành động không được hỗ trợ.');
    }
};

const E15_2 = () => {
    const [state, dispatch] = useReducer(quizReducer, initialState);

    const handleOptionSelect = (option) => {
        dispatch({ type: 'CHON_LUA', payload: option });
    };

    const handleNextQuestion = () => {
        dispatch({ type: 'CAU_HOI_TIEP_THEO' });
    };

    const handleRestartQuiz = () => {
        dispatch({ type: 'KHOI_DONG_LAI' });
    };

    return (
        <div>
            {state.showScore ? (
                <div>
                    <div>Điểm của bạn: {state.score}/{state.questions.length}</div>
                    <button onClick={handleRestartQuiz}>Bắt đầu lại</button>
                </div>
            ) : (
                <div>
                    <div>Câu hỏi {state.currentQuestion + 1}</div>
                    <div>{state.questions[state.currentQuestion].question}</div>
                    {state.questions[state.currentQuestion].options.map((option) => (
                        <button key={option} onClick={() => handleOptionSelect(option)}>
                            {option}
                        </button>
                    ))}
                    <button onClick={handleNextQuestion}>Tiếp theo</button>
                </div>
            )}
        </div>
    );
};

export default E15_2;
